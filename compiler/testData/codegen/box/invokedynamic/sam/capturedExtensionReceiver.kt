// TARGET_BACKEND: JVM
// JVM_TARGET: 1.8
// SAM_CONVERSIONS: INDY

// CHECK_BYTECODE_TEXT
// 1 java/lang/invoke/LambdaMetafactory

fun interface KRunnable {
    fun run()
}

fun runIt(kr: KRunnable) {
    kr.run()
}

class C(var value: String)

fun C.test(): String {
    runIt { value = "OK" }
    return value
}

fun box() = C("xxx").test()
