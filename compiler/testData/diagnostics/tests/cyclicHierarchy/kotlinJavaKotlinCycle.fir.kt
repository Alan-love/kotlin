// RUN_PIPELINE_TILL: FRONTEND
// FILE: I.kt

open class I : K() {
    fun foo() {}
}

// FILE: J.java

class J extends I {
    void bar() {}
}

// FILE: K.kt

open class K : <!CYCLIC_INHERITANCE_HIERARCHY!>J<!>() {
    fun baz() {}
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, javaType */
