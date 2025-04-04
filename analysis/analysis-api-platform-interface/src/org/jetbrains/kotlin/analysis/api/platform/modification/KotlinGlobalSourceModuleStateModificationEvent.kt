/*
 * Copyright 2010-2025 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.platform.modification

import org.jetbrains.kotlin.analysis.api.projectStructure.KaModule

/**
 * [KotlinGlobalSourceModuleStateModificationEvent] signals that source module settings or structure are changing possibly globally.
 *
 * The module structure and source code of all source [KaModule]s in the project should be considered modified when this event is received.
 * This includes source files being moved or removed, and source modules possibly being removed. Thus, all caches related to source module
 * structure and source code should be invalidated.
 *
 * Library modules (including library sources) do not need to be considered modified, so any caches related to library modules and their
 * contents may be kept.
 *
 * See [KotlinModificationEvent] for important contracts common to all modification events.
 */
public object KotlinGlobalSourceModuleStateModificationEvent : KotlinModificationEvent
