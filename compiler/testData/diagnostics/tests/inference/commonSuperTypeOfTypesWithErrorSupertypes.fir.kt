// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER

interface Foo<F> {
    fun getSum(): F = TODO()
}

fun <S> select(vararg args: S): S = TODO()

class Bar<B : <!CYCLIC_GENERIC_UPPER_BOUND!>B<!>> : Foo<B> {
    val v = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any")!>select(
        getSum(),
        42
    )<!>
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, integerLiteral, interfaceDeclaration, nullableType,
propertyDeclaration, typeConstraint, typeParameter, vararg */
