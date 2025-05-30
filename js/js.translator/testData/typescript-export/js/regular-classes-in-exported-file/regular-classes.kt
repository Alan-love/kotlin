// This file was generated automatically. See  generateTestDataForTypeScriptWithFileExport.kt
// DO NOT MODIFY IT MANUALLY.

// CHECK_TYPESCRIPT_DECLARATIONS
// RUN_PLAIN_BOX_FUNCTION
// SKIP_NODE_JS
// INFER_MAIN_MODULE
// MODULE: JS_TESTS
// FILE: regular-classes.kt

@file:JsExport

package foo


class A


class A1(val x: Int)


class A2(val x: String, var y: Boolean)


class A3 {
    val x: Int = 100
}


class A4<T>(val value: T) {
    fun test(): T = value
}

class A5 {
    companion object {
        val x = 10
    }
}


open class A6 {
    fun then(): Int = 42
    fun catch(): Int = 24
}


class GenericClassWithConstraint<T: A6>(val test: T)