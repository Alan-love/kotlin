// TARGET_BACKEND: JS_IR
// TARGET_BACKEND: WASM

// RUN_THIRD_PARTY_OPTIMIZER
// WASM_DCE_EXPECTED_OUTPUT_SIZE: wasm  19_316
// WASM_DCE_EXPECTED_OUTPUT_SIZE: mjs    5_386
// WASM_OPT_EXPECTED_OUTPUT_SIZE:        3_076

interface I {
    fun foo() = "OK"
}

abstract class A : I

class B : A()

class C : A() {
    override fun foo(): String {
        return "C::foo"
    }
}

fun box() = B().foo()
