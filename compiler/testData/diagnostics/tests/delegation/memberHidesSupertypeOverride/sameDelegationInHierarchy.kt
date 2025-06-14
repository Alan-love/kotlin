// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
public interface Base {
    fun test() = "Base"
}

class Delegate : Base {
    override fun test() = "Base"
}

public open class MyClass : Base by Delegate()

fun box(): String {
    <!DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE!>object<!> : MyClass(), Base by Delegate() {
    }
    return "OK"
}

/* GENERATED_FIR_TAGS: anonymousObjectExpression, classDeclaration, functionDeclaration, inheritanceDelegation,
interfaceDeclaration, override, stringLiteral */
