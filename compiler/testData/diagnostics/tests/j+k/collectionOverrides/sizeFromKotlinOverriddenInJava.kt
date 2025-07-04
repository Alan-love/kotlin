// DISABLE_JAVA_FACADE
// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: A.java

abstract public class A<F> extends MyList<F> {
    int getSize() { return 0; }
}

// FILE: main.kt

abstract class MyList<G> : Collection<G> {}

fun main(a: A<String>) {
    a.size
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, javaType, nullableType, typeParameter */
