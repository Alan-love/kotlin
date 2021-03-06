/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.declarations.persistent.carriers

import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol
import org.jetbrains.kotlin.ir.symbols.IrSymbol
import org.jetbrains.kotlin.ir.types.IrType

// Auto-generated by compiler/ir/ir.tree.persistent/generator/src/org/jetbrains/kotlin/ir/persistentIrGenerator/Main.kt. DO NOT EDIT!

internal interface FieldCarrier : DeclarationCarrier{
    val typeField: IrType
    val initializerField: IrExpressionBody?
    val correspondingPropertySymbolField: IrPropertySymbol?

    override fun clone(): FieldCarrier {
        return FieldCarrierImpl(
            lastModified,
            parentSymbolField,
            originField,
            annotationsField,
            typeField,
            initializerField,
            correspondingPropertySymbolField
        )
    }
}

internal class FieldCarrierImpl(
    override val lastModified: Int,
    override val parentSymbolField: IrSymbol?,
    override val originField: IrDeclarationOrigin,
    override val annotationsField: List<IrConstructorCall>,
    override val typeField: IrType,
    override val initializerField: IrExpressionBody?,
    override val correspondingPropertySymbolField: IrPropertySymbol?
) : FieldCarrier
