// FIR_IDENTICAL
// RUN_PIPELINE_TILL: FRONTEND
// DIAGNOSTICS: -UNUSED_PARAMETER

import kotlin.reflect.*

annotation class Ann(
        val kc1: KClass<*>,
        val kc2: KClass<*>,
        val kc3: KClass<*>,
        val c: Int,
        val cc: Int,
        val cn: Int,
        val ci: Int,
        val t1: Int,
        val t2: Int
)

class A
@Ann(
        Nested::class,
        Inner::class,
        Interface::class,
        CONST,
        Companion.CONST,
        Nested.CONST,
        Interface.CONST,
        <!UNRESOLVED_REFERENCE!>a<!>,
        <!UNRESOLVED_REFERENCE!>b<!>()
)
constructor() {

    @Ann(
            Nested::class,
            Inner::class,
            Interface::class,
            CONST,
            Companion.CONST,
            Nested.CONST,
            Interface.CONST,
            <!UNRESOLVED_REFERENCE!>a<!>,
            <!UNRESOLVED_REFERENCE!>b<!>()
    )
    constructor(dummy: Int) : this()

    class Nested {
        companion object {
            const val CONST = 2
        }
    }

    inner class Inner

    interface Interface {
        companion object {
            const val CONST = 3
        }
    }

    val a = 1
    fun b() = 2

    companion object {
        const val CONST = 1
        fun foo(): Nested = null!!
    }
}

/* GENERATED_FIR_TAGS: annotationDeclaration, checkNotNullCall, classDeclaration, classReference, companionObject, const,
functionDeclaration, inner, integerLiteral, interfaceDeclaration, nestedClass, objectDeclaration, primaryConstructor,
propertyDeclaration, secondaryConstructor, starProjection */
