// RUN_PIPELINE_TILL: FRONTEND
fun foo(): Int {
    val x: Any? = null
    val y = 2
    if (x == y) {
        return x <!UNRESOLVED_REFERENCE_WRONG_RECEIVER!>+<!> y
    }
    return y
}