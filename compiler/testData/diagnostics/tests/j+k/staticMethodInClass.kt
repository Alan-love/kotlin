// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: JavaClass.java

public class JavaClass {
    public static String testStatic() {
        return "OK";
    }

}

// FILE: 1.kt
import JavaClass.testStatic

fun test() {
    JavaClass.testStatic()
    testStatic()
}

/* GENERATED_FIR_TAGS: flexibleType, functionDeclaration, javaFunction */
