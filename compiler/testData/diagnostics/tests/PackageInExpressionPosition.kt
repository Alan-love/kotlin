// RUN_PIPELINE_TILL: FRONTEND
// COMPARE_WITH_LIGHT_TREE
package foo

class X {}

val s = <!EXPRESSION_EXPECTED_PACKAGE_FOUND!>java<!>
val ss = <!NO_COMPANION_OBJECT!>System<!>
val sss = <!NO_COMPANION_OBJECT!>X<!>
val x = "${<!NO_COMPANION_OBJECT!>System<!>}"
val xs = java.<!EXPRESSION_EXPECTED_PACKAGE_FOUND!>lang<!>
val xss = java.lang.<!NO_COMPANION_OBJECT!>System<!>
val xsss = foo.<!NO_COMPANION_OBJECT!>X<!>
val xssss = <!EXPRESSION_EXPECTED_PACKAGE_FOUND!>foo<!>
val f = { <!NO_COMPANION_OBJECT!>System<!> }

fun main() {
    <!EXPRESSION_EXPECTED_PACKAGE_FOUND!>java<!> = null
    <!NO_COMPANION_OBJECT!>System<!> = null
    <!NO_COMPANION_OBJECT!>System<!>!!
    java.lang.<!NO_COMPANION_OBJECT!>System<!> = null
    java.lang.<!NO_COMPANION_OBJECT!>System<!>!!
    <!NO_COMPANION_OBJECT!>System<!> is Int
    <!INVISIBLE_MEMBER!>System<!>()
    (<!NO_COMPANION_OBJECT!>System<!>)
    <!REDUNDANT_LABEL_WARNING!>foo@<!> <!NO_COMPANION_OBJECT!>System<!>
    null in <!NO_COMPANION_OBJECT!>System<!>
}

/* GENERATED_FIR_TAGS: assignment, checkNotNullCall, classDeclaration, functionDeclaration, isExpression, javaFunction,
lambdaLiteral, nullableType, propertyDeclaration */
