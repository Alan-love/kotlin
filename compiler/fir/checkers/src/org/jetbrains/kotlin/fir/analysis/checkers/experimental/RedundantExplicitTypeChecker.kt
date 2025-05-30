/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.analysis.checkers.experimental

import org.jetbrains.kotlin.KtFakeSourceElementKind
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirPropertyChecker
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors
import org.jetbrains.kotlin.fir.declarations.FirProperty
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.resolve.fullyExpandedType
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.StandardClassIds
import org.jetbrains.kotlin.types.ConstantValueKind

object RedundantExplicitTypeChecker : FirPropertyChecker(MppCheckerKind.Common) {
    context(context: CheckerContext, reporter: DiagnosticReporter)
    override fun check(declaration: FirProperty) {
        if (!declaration.isLocal) return
        if (declaration.returnTypeRef.source == null) return

        val initializer = declaration.initializer?.unwrapSmartcastExpression() ?: return
        val typeReference = declaration.returnTypeRef.takeUnless { it is FirErrorTypeRef } ?: return

        if (typeReference.source?.kind is KtFakeSourceElementKind) return

        val type = typeReference.coneType.fullyExpandedType(context.session)

        if (type.abbreviatedType != null) return
        if (typeReference.annotations.isNotEmpty()) return

        when (initializer) {
            is FirLiteralExpression -> {
                when (initializer.source?.elementType) {
                    KtNodeTypes.BOOLEAN_CONSTANT -> {
                        if (!type.isSame(StandardClassIds.Boolean)) return
                    }
                    KtNodeTypes.INTEGER_CONSTANT -> {
                        if (initializer.kind == ConstantValueKind.Long) {
                            if (!type.isSame(StandardClassIds.Long)) return
                        } else {
                            if (!type.isSame(StandardClassIds.Int)) return
                        }
                    }
                    KtNodeTypes.FLOAT_CONSTANT -> {
                        if (initializer.kind == ConstantValueKind.Float) {
                            if (!type.isSame(StandardClassIds.Float)) return
                        } else {
                            if (!type.isSame(StandardClassIds.Double)) return
                        }
                    }
                    KtNodeTypes.CHARACTER_CONSTANT -> {
                        if (!type.isSame(StandardClassIds.Char)) return
                    }
                    KtNodeTypes.STRING_TEMPLATE -> {
                        if (!type.isSame(StandardClassIds.String)) return
                    }
                    else -> return
                }
            }
            is FirFunctionCall -> {
                if (!type.hasSameNameWithoutModifiers(initializer.calleeReference.name)) return
            }
            is FirGetClassCall -> {
                return
            }
            is FirResolvedQualifier -> {
                if (!type.isSame(initializer.classId)) return
            }
            is FirStringConcatenationCall -> {
                if (!type.isSame(StandardClassIds.String)) return
            }
            else -> return
        }

        reporter.reportOn(declaration.returnTypeRef.source, FirErrors.REDUNDANT_EXPLICIT_TYPE)
    }

    private fun ConeKotlinType.isSame(other: ClassId?): Boolean {
        if (this.isMarkedNullable) return false
        if (this.classId == other) return true
        return false
    }

    private fun ConeKotlinType.hasSameNameWithoutModifiers(name: Name): Boolean =
        this is ConeClassLikeType && lookupTag.name == name && typeArguments.isEmpty() && !isMarkedNullable
}