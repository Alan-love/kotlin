// JVM_DEFAULT_MODE: enable
// TARGET_BACKEND: JVM
// IGNORE_BACKEND: ANDROID
// JVM_TARGET: 1.8
// WITH_REFLECT
// FULL_JDK

// This test is checking that there are overrides of 'test' generated in TestClass and TestClass2 (see KT-73954), but not Test3.
// CHECK_BYTECODE_LISTING

interface Test {
    fun test(): String = "Test"
}

open class TestClass : Test

interface Test2 : Test {
    override fun test(): String = "OK"
}

interface Test3 : Test2

class TestClass2 : TestClass(), Test3

fun box(): String {
    return TestClass2().test()
}
