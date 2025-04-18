/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.library.abi.impl

import org.jetbrains.kotlin.library.abi.*
import org.jetbrains.kotlin.metadata.deserialization.Flags.FlagField

@ExperimentalLibraryAbiReader
object AbiSignatureVersions {
    enum class Supported(override val versionNumber: Int, override val description: String) : AbiSignatureVersion {
        V1(1, "The signatures with hashes"),
        V2(2, "The self-descriptive signatures (with mangled names)");

        override val isSupportedByAbiReader get() = true
    }

    private data class Unsupported(override val versionNumber: Int) : AbiSignatureVersion {
        override val isSupportedByAbiReader get() = false
        override val description: String? get() = null
    }

    fun resolveByVersionNumber(versionNumber: Int): AbiSignatureVersion =
        Supported.entries.firstOrNull { it.versionNumber == versionNumber } ?: Unsupported(versionNumber)
}

@ExperimentalLibraryAbiReader
internal data class AbiSignaturesImpl(private val signatureV1: String?, private val signatureV2: String?) : AbiSignatures {
    override operator fun get(signatureVersion: AbiSignatureVersion): String? = when (signatureVersion) {
        is AbiSignatureVersions.Supported -> when (signatureVersion) {
            AbiSignatureVersions.Supported.V1 -> signatureV1
            AbiSignatureVersions.Supported.V2 -> signatureV2
        }
        else -> error("Unsupported signature version: $signatureVersion")
    }
}

@ExperimentalLibraryAbiReader
internal class AbiAnnotationListImpl(private val annotations: List<AbiAnnotation>) : AbiAnnotatedEntity {
    @Deprecated(level = DeprecationLevel.WARNING, message = "Use annotatedWith instead.", replaceWith = ReplaceWith("annotatedWith"))
    override fun hasAnnotation(annotationClassName: AbiQualifiedName) = annotatedWith().any { it.qualifiedName == annotationClassName }

    override fun annotatedWith() = annotations

    companion object {
        val EMPTY = AbiAnnotationListImpl(emptyList())
    }
}

@ExperimentalLibraryAbiReader
internal data class AbiAnnotationImpl(override val qualifiedName: AbiQualifiedName) : AbiAnnotation

@ExperimentalLibraryAbiReader
internal class AbiTopLevelDeclarationsImpl(
    override val declarations: List<AbiDeclaration>
) : AbiTopLevelDeclarations

@ExperimentalLibraryAbiReader
internal class AbiClassImpl(
    override val qualifiedName: AbiQualifiedName,
    override val signatures: AbiSignatures,
    annotations: AbiAnnotationListImpl,
    modality: AbiModality,
    kind: AbiClassKind,
    isInner: Boolean,
    isValue: Boolean,
    isFunction: Boolean,
    override val superTypes: List<AbiType>,
    override val declarations: List<AbiDeclaration>,
    override val typeParameters: List<AbiTypeParameter>
) : AbiClass, AbiAnnotatedEntity by annotations {
    private val flags = IS_INNER.toFlags(isInner) or
            IS_VALUE.toFlags(isValue) or
            IS_FUNCTION.toFlags(isFunction) or
            MODALITY.toFlags(modality) or
            CLASS_KIND.toFlags(kind)

    override val modality get() = MODALITY.get(flags)
    override val kind get() = CLASS_KIND.get(flags)
    override val isInner get() = IS_INNER.get(flags)
    override val isValue get() = IS_VALUE.get(flags)
    override val isFunction get() = IS_FUNCTION.get(flags)

    companion object {
        private val IS_INNER = FlagField.booleanFirst()
        private val IS_VALUE = FlagField.booleanAfter(IS_INNER)
        private val IS_FUNCTION = FlagField.booleanAfter(IS_VALUE)
        private val MODALITY = FlagFieldEx.after<AbiModality>(IS_FUNCTION)
        private val CLASS_KIND = FlagFieldEx.after<AbiClassKind>(MODALITY)
    }
}

@ExperimentalLibraryAbiReader
internal class AbiEnumEntryImpl(
    override val qualifiedName: AbiQualifiedName,
    override val signatures: AbiSignatures,
    annotations: AbiAnnotationListImpl
) : AbiEnumEntry, AbiAnnotatedEntity by annotations

@ExperimentalLibraryAbiReader
internal class AbiConstructorImpl(
    override val qualifiedName: AbiQualifiedName,
    override val signatures: AbiSignatures,
    annotations: AbiAnnotationListImpl,
    isInline: Boolean,
    override val valueParameters: List<AbiValueParameter>
) : AbiFunction, AbiAnnotatedEntity by annotations {
    private val flags = IS_INLINE.toFlags(isInline)

    override val modality get() = AbiModality.FINAL // No need to render modality for constructors.
    override val isConstructor get() = true
    override val isInline get() = IS_INLINE.get(flags)
    override val isSuspend get() = false

    @Suppress("OVERRIDE_DEPRECATION")
    override val hasExtensionReceiverParameter get() = false

    @Suppress("OVERRIDE_DEPRECATION")
    override val contextReceiverParametersCount get() = valueParameters.count { it.kind == AbiValueParameterKind.CONTEXT }
    override val returnType get() = null // No need to render return type for constructors.
    override val typeParameters get() = emptyList<AbiTypeParameter>()

    companion object {
        private val IS_INLINE = FlagField.booleanFirst()
    }
}

@ExperimentalLibraryAbiReader
internal class AbiFunctionImpl(
    override val qualifiedName: AbiQualifiedName,
    override val signatures: AbiSignatures,
    annotations: AbiAnnotationListImpl,
    modality: AbiModality,
    isInline: Boolean,
    isSuspend: Boolean,
    override val typeParameters: List<AbiTypeParameter>,
    override val valueParameters: List<AbiValueParameter>,
    override val returnType: AbiType?
) : AbiFunction, AbiAnnotatedEntity by annotations {
    private val flags = IS_INLINE.toFlags(isInline) or
            IS_SUSPEND.toFlags(isSuspend) or
            MODALITY.toFlags(modality)

    override val modality get() = MODALITY.get(flags)
    override val isConstructor get() = false
    override val isInline get() = IS_INLINE.get(flags)
    override val isSuspend get() = IS_SUSPEND.get(flags)

    @Deprecated("Use annotatedWith instead.", replaceWith = ReplaceWith("annotatedWith"), level = DeprecationLevel.WARNING)

    @Suppress("OVERRIDE_DEPRECATION")
    override val hasExtensionReceiverParameter get() = valueParameters.any { it.kind == AbiValueParameterKind.EXTENSION_RECEIVER }

    @Suppress("OVERRIDE_DEPRECATION")
    override val contextReceiverParametersCount get() = valueParameters.count { it.kind == AbiValueParameterKind.CONTEXT }

    companion object {
        /** JVM allows max 255 parameters for a function. Storing such a number requires just 8 bits. */
        const val BITS_ENOUGH_FOR_STORING_PARAMETERS_COUNT = 8

        private val IS_INLINE = FlagField.booleanFirst()
        private val IS_SUSPEND = FlagField.booleanAfter(IS_INLINE)
        private val MODALITY = FlagFieldEx.after<AbiModality>(IS_SUSPEND)
    }
}

@ExperimentalLibraryAbiReader
internal class AbiValueParameterImpl(
    kind: AbiValueParameterKind,
    override val type: AbiType,
    isVararg: Boolean,
    hasDefaultArg: Boolean,
    isNoinline: Boolean,
    isCrossinline: Boolean
) : AbiValueParameter {
    private val flags = IS_VARARG.toFlags(isVararg) or
            HAS_DEFAULT_ARG.toFlags(hasDefaultArg) or
            IS_NOINLINE.toFlags(isNoinline) or
            IS_CROSSINLINE.toFlags(isCrossinline) or
            PARAMETER_KIND.toFlags(kind)

    override val kind: AbiValueParameterKind get() = PARAMETER_KIND.get(flags)
    override val isVararg get() = IS_VARARG.get(flags)
    override val hasDefaultArg get() = HAS_DEFAULT_ARG.get(flags)
    override val isNoinline get() = IS_NOINLINE.get(flags)
    override val isCrossinline get() = IS_CROSSINLINE.get(flags)

    companion object {
        private val IS_VARARG = FlagField.booleanFirst()
        private val HAS_DEFAULT_ARG = FlagField.booleanAfter(IS_VARARG)
        private val IS_NOINLINE = FlagField.booleanAfter(HAS_DEFAULT_ARG)
        private val IS_CROSSINLINE = FlagField.booleanAfter(IS_NOINLINE)
        private val PARAMETER_KIND = FlagFieldEx.after<AbiValueParameterKind>(IS_CROSSINLINE)
    }
}

@ExperimentalLibraryAbiReader
internal class AbiPropertyImpl(
    override val qualifiedName: AbiQualifiedName,
    override val signatures: AbiSignatures,
    annotations: AbiAnnotationListImpl,
    modality: AbiModality,
    kind: AbiPropertyKind,
    override val getter: AbiFunction?,
    override val setter: AbiFunction?,
    override val backingField: AbiField?
) : AbiProperty, AbiAnnotatedEntity by annotations {
    private val flags = MODALITY.toFlags(modality) or PROPERTY_KIND.toFlags(kind)

    override val modality get() = MODALITY.get(flags)
    override val kind get() = PROPERTY_KIND.get(flags)

    companion object {
        private val MODALITY = FlagFieldEx.first<AbiModality>()
        private val PROPERTY_KIND = FlagFieldEx.after<AbiPropertyKind>(MODALITY)
    }
}

@ExperimentalLibraryAbiReader
internal class AbiFieldImpl(annotations: AbiAnnotationListImpl) : AbiField, AbiAnnotatedEntity by annotations

@ExperimentalLibraryAbiReader
internal class AbiTypeParameterImpl(
    override val tag: String,
    variance: AbiVariance,
    isReified: Boolean,
    override val upperBounds: List<AbiType>
) : AbiTypeParameter {
    private val flags = IS_REIFIED.toFlags(isReified) or VARIANCE.toFlags(variance)

    override val variance = VARIANCE.get(flags)
    override val isReified = IS_REIFIED.get(flags)

    companion object {
        private val IS_REIFIED = FlagField.booleanFirst()
        private val VARIANCE = FlagFieldEx.after<AbiVariance>(IS_REIFIED)
    }
}

@ExperimentalLibraryAbiReader
internal object DynamicTypeImpl : AbiType.Dynamic

@ExperimentalLibraryAbiReader
internal object ErrorTypeImpl : AbiType.Error

@ExperimentalLibraryAbiReader
internal class SimpleTypeImpl(
    override val classifierReference: AbiClassifierReference,
    override val arguments: List<AbiTypeArgument>,
    override val nullability: AbiTypeNullability
) : AbiType.Simple

@ExperimentalLibraryAbiReader
internal object StarProjectionImpl : AbiTypeArgument.StarProjection

@ExperimentalLibraryAbiReader
internal class TypeProjectionImpl(
    override val type: AbiType,
    override val variance: AbiVariance
) : AbiTypeArgument.TypeProjection

@ExperimentalLibraryAbiReader
internal class ClassReferenceImpl(override val className: AbiQualifiedName) : AbiClassifierReference.ClassReference

@ExperimentalLibraryAbiReader
internal class TypeParameterReferenceImpl(override val tag: String) : AbiClassifierReference.TypeParameterReference
