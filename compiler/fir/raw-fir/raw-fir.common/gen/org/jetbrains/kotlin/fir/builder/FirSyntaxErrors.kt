/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.builder

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.diagnostics.*
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1
import org.jetbrains.kotlin.diagnostics.KtDiagnosticsContainer
import org.jetbrains.kotlin.diagnostics.Severity.ERROR
import org.jetbrains.kotlin.diagnostics.SourceElementPositioningStrategies
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory

/**
 * Generated from: [org.jetbrains.kotlin.fir.builder.SYNTAX_DIAGNOSTIC_LIST]
 */
@Suppress("IncorrectFormatting")
object FirSyntaxErrors : KtDiagnosticsContainer() {
    // Syntax
    val SYNTAX: KtDiagnosticFactory1<String> = KtDiagnosticFactory1("SYNTAX", ERROR, SourceElementPositioningStrategies.SYNTAX_ERROR, PsiElement::class, getRendererFactory())

    override fun getRendererFactory(): BaseDiagnosticRendererFactory = FirSyntaxErrorsDefaultMessages
}
