// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
<!WRONG_MODIFIER_TARGET!>sealed<!> enum class SealedEnum {
    FIRST, 
    SECOND;

    class Derived: SealedEnum()
}

/* GENERATED_FIR_TAGS: classDeclaration, enumDeclaration, enumEntry, nestedClass, sealed */
