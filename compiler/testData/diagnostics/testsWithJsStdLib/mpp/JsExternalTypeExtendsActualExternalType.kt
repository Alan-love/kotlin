// RUN_PIPELINE_TILL: KLIB
// LANGUAGE: +MultiPlatformProjects
// MODULE: commonjs
// FILE: commonjs.kt

expect interface ExternalInterface

external class ExternalClass: ExternalInterface

// MODULE: js()()(commonjs)
// FILE: js.kt

actual external interface ExternalInterface
