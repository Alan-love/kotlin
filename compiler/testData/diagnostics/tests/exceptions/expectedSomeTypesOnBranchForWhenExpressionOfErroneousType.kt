// RUN_PIPELINE_TILL: FRONTEND
// ISSUE: KT-51274

fun test() {
    val x = <!UNRESOLVED_REFERENCE!>unresolved<!>()
    val y = when (<!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!>) {
        is String -> <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!>
        else -> throw Exception()
    }
}

/* GENERATED_FIR_TAGS: functionDeclaration, isExpression, localProperty, propertyDeclaration, smartcast, whenExpression,
whenWithSubject */
