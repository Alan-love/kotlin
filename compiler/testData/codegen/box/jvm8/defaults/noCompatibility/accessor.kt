// JVM_DEFAULT_MODE: no-compatibility
// TARGET_BACKEND: JVM
// JVM_TARGET: 1.8
// WITH_STDLIB

var storage = "fail"

interface Test {

    private var foo: String
        get() = storage
        set(value) {
            storage = value
        }

    private fun bar(): String {
        return "K"
    }

    fun call(): String {
        return {
            foo = "O"
            foo + bar()
        }.let { it() }
    }
}

class TestClass : Test {

}

fun box(): String {
    return TestClass().call()
}
