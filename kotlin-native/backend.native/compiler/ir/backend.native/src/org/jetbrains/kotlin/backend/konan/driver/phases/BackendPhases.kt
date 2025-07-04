/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.konan.driver.phases

import org.jetbrains.kotlin.backend.common.phaser.KotlinBackendIrHolder
import org.jetbrains.kotlin.backend.common.phaser.PhaseEngine
import org.jetbrains.kotlin.backend.common.phaser.createSimpleNamedCompilerPhase
import org.jetbrains.kotlin.backend.konan.KonanCompilationException
import org.jetbrains.kotlin.backend.konan.NativeGenerationState
import org.jetbrains.kotlin.backend.konan.NativePreSerializationLoweringContext
import org.jetbrains.kotlin.backend.konan.OutputFiles
import org.jetbrains.kotlin.backend.konan.driver.PhaseContext
import org.jetbrains.kotlin.backend.konan.driver.utilities.getDefaultIrActions
import org.jetbrains.kotlin.backend.konan.ir.KonanSymbols
import org.jetbrains.kotlin.backend.konan.lower.ExpectToActualDefaultValueCopier
import org.jetbrains.kotlin.backend.konan.lower.SpecialBackendChecksTraversal
import org.jetbrains.kotlin.backend.konan.makeEntryPoint
import org.jetbrains.kotlin.backend.konan.objcexport.createTestBundle
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.fir.FirDiagnosticsCompilerResultsReporter
import org.jetbrains.kotlin.cli.common.runPreSerializationLoweringPhases
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.languageVersionSettings
import org.jetbrains.kotlin.config.messageCollector
import org.jetbrains.kotlin.descriptors.impl.PackageFragmentDescriptorImpl
import org.jetbrains.kotlin.diagnostics.DiagnosticReporterFactory
import org.jetbrains.kotlin.ir.IrBuiltIns
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.IrFileEntry
import org.jetbrains.kotlin.ir.KtDiagnosticReporterWithImplicitIrBasedContext
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl
import org.jetbrains.kotlin.ir.inline.konan.nativeLoweringsOfTheFirstPhase
import org.jetbrains.kotlin.ir.util.NaiveSourceBasedFileEntryImpl
import org.jetbrains.kotlin.ir.util.addChild
import org.jetbrains.kotlin.ir.util.addFile
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.scopes.MemberScope

internal data class SpecialBackendChecksInput(
        val irModule: IrModuleFragment,
        val irBuiltIns: IrBuiltIns,
        val symbols: KonanSymbols,
) : KotlinBackendIrHolder {
    override val kotlinIr: IrElement
        get() = irModule
}

internal val SpecialBackendChecksPhase = createSimpleNamedCompilerPhase<PhaseContext, SpecialBackendChecksInput>(
        "SpecialBackendChecks",
        preactions = getDefaultIrActions(),
        postactions = getDefaultIrActions(),
) { context, input ->
    SpecialBackendChecksTraversal(context, input.symbols, input.irBuiltIns).lower(input.irModule)
}

internal val K2SpecialBackendChecksPhase = createSimpleNamedCompilerPhase<PhaseContext, Fir2IrOutput>(
        "SpecialBackendChecks",
) { context, input ->
    val moduleFragment = input.fir2irActualizedResult.irModuleFragment
    SpecialBackendChecksTraversal(
            context,
            input.symbols,
            input.fir2irActualizedResult.irBuiltIns,
    ).lower(moduleFragment)
}

internal val CopyDefaultValuesToActualPhase = createSimpleNamedCompilerPhase<PhaseContext, Pair<IrModuleFragment, IrBuiltIns>>(
        name = "CopyDefaultValuesToActual",
        preactions = getDefaultIrActions(),
        postactions = getDefaultIrActions(),
) { _, (irModule, irBuiltins) ->
    ExpectToActualDefaultValueCopier(irModule, irBuiltins).process()
}

internal fun <T : PhaseContext> PhaseEngine<T>.runSpecialBackendChecks(irModule: IrModuleFragment, irBuiltIns: IrBuiltIns, symbols: KonanSymbols) {
    runPhase(SpecialBackendChecksPhase, SpecialBackendChecksInput(irModule, irBuiltIns, symbols))
}

internal fun <T : PhaseContext> PhaseEngine<T>.runK2SpecialBackendChecks(fir2IrOutput: Fir2IrOutput) {
    runPhase(K2SpecialBackendChecksPhase, fir2IrOutput)
}

internal fun <T : PhaseContext> PhaseEngine<T>.runPreSerializationLowerings(fir2IrOutput: Fir2IrOutput, environment: KotlinCoreEnvironment): Fir2IrOutput {
    val diagnosticReporter = DiagnosticReporterFactory.createReporter(environment.configuration.messageCollector)
    val irDiagnosticReporter = KtDiagnosticReporterWithImplicitIrBasedContext(
            diagnosticReporter,
            environment.configuration.languageVersionSettings
    )
    val loweringContext = NativePreSerializationLoweringContext(
            fir2IrOutput.fir2irActualizedResult.irBuiltIns,
            environment.configuration,
            irDiagnosticReporter,
    )
    val preSerializationLowered = newEngine(loweringContext) { engine ->
        engine.runPreSerializationLoweringPhases(
                fir2IrOutput.fir2irActualizedResult,
                nativeLoweringsOfTheFirstPhase(environment.configuration.languageVersionSettings),
        )
    }
    // TODO: After KT-73624, generate native diagnostic tests for `compiler/testData/diagnostics/irInliner/syntheticAccessors`
    FirDiagnosticsCompilerResultsReporter.reportToMessageCollector(
            diagnosticReporter,
            environment.configuration.messageCollector,
            environment.configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME),
    )
    if (diagnosticReporter.hasErrors) {
        throw KonanCompilationException("Compilation failed: there were some diagnostics during IR Inliner")
    }

    return fir2IrOutput.copy(
            fir2irActualizedResult = preSerializationLowered,
    )
}

internal val EntryPointPhase = createSimpleNamedCompilerPhase<NativeGenerationState, IrModuleFragment>(
        name = "addEntryPoint",
        preactions = getDefaultIrActions(),
        postactions = getDefaultIrActions(),
) { context, module ->
    val parent = context.context
    val entryPoint = parent.symbols.entryPoint!!.owner
    val file: IrFile = if (context.llvmModuleSpecification.containsDeclaration(entryPoint)) {
        entryPoint.file
    } else {
        // `main` function is compiled to other LLVM module.
        // For example, test running support uses `main` defined in stdlib.
        module.addFile(NaiveSourceBasedFileEntryImpl("entryPointOwner"), FqName("kotlin.native.internal.abi"))
    }

    file.addChild(makeEntryPoint(context))
}

internal val CreateTestBundlePhase = createSimpleNamedCompilerPhase<PhaseContext, FrontendPhaseOutput.Full>(
        "CreateTestBundlePhase",
) { context, input ->
    val config = context.config
    val output = OutputFiles(config.outputPath, config.target, config.produce).mainFile
    createTestBundle(config, input.moduleDescriptor, output)
}

private fun IrModuleFragment.addFile(fileEntry: IrFileEntry, packageFqName: FqName): IrFile {
    val packageFragmentDescriptor = object : PackageFragmentDescriptorImpl(this.descriptor, packageFqName) {
        override fun getMemberScope(): MemberScope = MemberScope.Empty
    }

    return IrFileImpl(fileEntry, packageFragmentDescriptor).also(this::addFile)
}
