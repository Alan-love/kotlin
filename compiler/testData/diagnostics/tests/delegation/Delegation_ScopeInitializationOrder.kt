// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
interface A {
  fun foo() {}
}

interface B : A {}

class C(b : B) : B by b {

}

