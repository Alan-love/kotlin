// RUN_PIPELINE_TILL: FRONTEND
class A {
    <!MUST_BE_INITIALIZED_OR_BE_ABSTRACT!>val number: Number<!>
        <!EXPLICIT_BACKING_FIELDS_UNSUPPORTED!>field = 1<!>
}

/* GENERATED_FIR_TAGS: classDeclaration, integerLiteral, propertyDeclaration */
