// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// DIAGNOSTICS: -UNUSED_PARAMETER
@DslMarker
@Target(AnnotationTarget.TYPE)
annotation class Ann

class A {
    fun a() = 1
}

class B {
    fun b() = 2
}

fun foo(x: (@Ann A).() -> Unit) {}
fun bar(x: (@Ann B).() -> Unit) {}

fun test() {
    foo {
        a()
        bar {
            <!DSL_SCOPE_VIOLATION!>a<!>()
            this@foo.a()
            b()
        }
    }
}

/* GENERATED_FIR_TAGS: annotationDeclaration, classDeclaration, functionDeclaration, functionalType, integerLiteral,
lambdaLiteral, thisExpression, typeWithExtension */
