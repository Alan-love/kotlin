// This file was generated automatically. See  generateTestDataForTypeScriptWithFileExport.kt
// DO NOT MODIFY IT MANUALLY.

// CHECK_TYPESCRIPT_DECLARATIONS
// RUN_PLAIN_BOX_FUNCTION
// SKIP_NODE_JS
// INFER_MAIN_MODULE

// DIAGNOSTICS: -EXPORTING_JS_NAME_CLASH_ES
// ^ This warning only concernes the ES6 mode, but since it's reported during KLIB serialization, the module system is unknown at that
//   point. Additionally, the same KLIB can be used for building JS in different module systems. Therefore, the warning is always written,
//   regardless of the project's module system.


// MODULE: JS_TESTS
// FILE: file1.kt

@file:JsExport

package foo.bar.baz

import a.b.*
import C3


data class C1(val value: String)


fun f(x1: C1, x2: C2, x3: C3): String {
    return "foo.bar.baz.f($x1, $x2, $x3)"
}

// FILE: file2.kt

@file:JsExport

package a.b

import foo.bar.baz.*
import C3


data class C2(val value: String)

fun f(x1: C1, x2: C2, x3: C3): String {
    return "a.b.f($x1, $x2, $x3)"
}

// FILE: file3.kt

@file:JsExport

import a.b.*
import foo.bar.baz.*


data class C3(val value: String)


fun f(x1: C1, x2: C2, x3: C3): String {
    return "f($x1, $x2, $x3)"
}