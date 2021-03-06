/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.asJava.classes

import org.jetbrains.kotlin.config.JvmDefaultMode
import org.jetbrains.kotlin.psi.KtClassOrObject

// light class for top level or (inner/nested of top level) source declarations
open class KtLightClassImpl @JvmOverloads constructor(
    classOrObject: KtClassOrObject,
    jvmDefaultMode: JvmDefaultMode,
    forceUsingOldLightClasses: Boolean = false
) : KtLightClassForSourceDeclaration(classOrObject, jvmDefaultMode, forceUsingOldLightClasses) {
    override fun getQualifiedName() = classOrObject.fqName?.asString()

    override fun getParent() = if (classOrObject.isTopLevel())
        containingFile
    else
        containingClass

    override fun copy() = KtLightClassImpl(classOrObject.copy() as KtClassOrObject, jvmDefaultMode)
}
