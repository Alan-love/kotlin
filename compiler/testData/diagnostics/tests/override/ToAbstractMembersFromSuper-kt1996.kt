// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// http://youtrack.jetbrains.com/issue/KT-1996

interface Foo {
    fun foo(): Unit
}

interface Bar {
    fun foo(): Unit
}

<!ABSTRACT_MEMBER_NOT_IMPLEMENTED!>class Baz<!> : Foo, Bar

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, interfaceDeclaration */
