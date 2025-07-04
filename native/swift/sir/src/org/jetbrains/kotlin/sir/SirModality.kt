/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.sir

enum class SirModality {
    OPEN,
    FINAL,
    UNSPECIFIED,
}

val SirClassMemberDeclaration.effectiveModality: SirModality
    get() = when (val parent = this.parent) {
        is SirClass -> when (parent.modality) {
            SirModality.FINAL -> SirModality.FINAL
            else -> this.modality
        }
        is SirExtension -> SirModality.FINAL
        is SirProtocol -> SirModality.OPEN
        else -> this.modality
    }