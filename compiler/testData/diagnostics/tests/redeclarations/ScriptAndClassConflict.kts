// RUN_PIPELINE_TILL: FRONTEND
// FILE: f1.kt
package test

class <!PACKAGE_OR_CLASSIFIER_REDECLARATION!>A<!>
class F1

// FILE: A.kts
package test

val x = 1

class F1

/* GENERATED_FIR_TAGS: classDeclaration, integerLiteral, localProperty, propertyDeclaration */
