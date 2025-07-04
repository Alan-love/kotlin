// RUN_PIPELINE_TILL: FRONTEND

class A {
    operator fun component1() = 1
    operator fun component2() = ""
}

class C {
    operator fun iterator(): Iterator<A> = null!!
}

fun test() {
    for ((x, _) in C()) {
        foo(x, <!UNRESOLVED_REFERENCE!>_<!>)
    }

    for ((_, y) in C()) {
        foo(<!UNRESOLVED_REFERENCE!>_<!>, y)
    }

    for ((_, _) in C()) {
        foo(<!UNRESOLVED_REFERENCE!>_<!>, <!UNRESOLVED_REFERENCE!>_<!>)
    }

    for ((_ : Int, _ : String) in C()) {
        foo(<!UNRESOLVED_REFERENCE!>_<!>, <!UNRESOLVED_REFERENCE!>_<!>)
    }

    for ((_ : String, _ : Int) in <!COMPONENT_FUNCTION_RETURN_TYPE_MISMATCH, COMPONENT_FUNCTION_RETURN_TYPE_MISMATCH!>C()<!>) {
        foo(<!UNRESOLVED_REFERENCE!>_<!>, <!UNRESOLVED_REFERENCE!>_<!>)
    }

    val (x, _) = A()
    val (_, y) = A()

    foo(x, y)
    foo(x, <!UNRESOLVED_REFERENCE!>_<!>)
    foo(<!UNRESOLVED_REFERENCE!>_<!>, y)

    val (<!REDECLARATION!>`_`<!>, z) = A()

    foo(<!UNDERSCORE_USAGE_WITHOUT_BACKTICKS!>_<!>, z)

    val (_, <!NAME_SHADOWING, REDECLARATION!>`_`<!>) = A()

    foo(<!TYPE_MISMATCH, UNDERSCORE_USAGE_WITHOUT_BACKTICKS!>_<!>, y)

    val (unused, _) = A()
}

fun foo(x: Int, y: String) {}

/* GENERATED_FIR_TAGS: checkNotNullCall, classDeclaration, destructuringDeclaration, forLoop, functionDeclaration,
integerLiteral, localProperty, operator, propertyDeclaration, stringLiteral, unnamedLocalVariable */
