// RUN_PIPELINE_TILL: BACKEND
open class A {
    fun String.foo(from: String, to: String): Int {
        return 1
    }

    fun <T> T.foo(from: String, to: String): Int {
        return 1
    }
}

class B : A()

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, integerLiteral, nullableType,
typeParameter */
