// RUN_PIPELINE_TILL: BACKEND
fun foo() {
    val x = object {
        fun sss() = abc()
        fun abc() = 1
    }
    val g = x.sss()
}
