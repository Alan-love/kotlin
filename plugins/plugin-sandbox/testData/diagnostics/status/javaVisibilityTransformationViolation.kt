// ISSUE: KT-74098
// FILE: main.kt
fun function() {
    JavaClass.<!CANNOT_INFER_PARAMETER_TYPE!>Nested<!>()
}

// FILE: JavaClass.java
public class JavaClass<T extends JavaClass.Neste> extends JavaClass.Nested<T> {
    public static class Nested<T extends JavaClass.Neste> {
    }
}
