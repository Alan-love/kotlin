// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -EXPOSED_PARAMETER_TYPE
// LANGUAGE: +ForbidExposingLessVisibleTypesInInline

private class S public constructor() {
    fun a() {

    }
}

internal inline fun x(s: S, z: () -> Unit) {
    z()
    <!PRIVATE_CLASS_MEMBER_FROM_INLINE!>S<!>()
    s.<!PRIVATE_CLASS_MEMBER_FROM_INLINE!>a<!>()
    test()
}

private inline fun x2(s: S, z: () -> Unit) {
    z()
    S()
    s.a()
    test()
}

private fun test(): S {
    return S()
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, functionalType, inline, primaryConstructor */
