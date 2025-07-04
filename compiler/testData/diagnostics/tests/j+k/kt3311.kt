// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: Super.java
public class Super {
    public boolean foo;
    public boolean bar;

    public void setFoo(boolean foo) {
        this.foo = foo;
    }
}

// FILE: b.kt
public class Sub: Super() {
}

fun main() {
    val x = Sub()
    x.foo = true
    x.bar = true
}

/* GENERATED_FIR_TAGS: assignment, classDeclaration, functionDeclaration, javaProperty, javaType, localProperty,
propertyDeclaration */
