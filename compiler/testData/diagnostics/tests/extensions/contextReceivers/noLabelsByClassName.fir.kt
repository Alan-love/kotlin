// RUN_PIPELINE_TILL: FRONTEND
// ISSUE: KT-63068
fun Int.f() {
    this<!UNRESOLVED_LABEL!>@Int<!>
}

var Int.p: Int
    get() {
        this<!UNRESOLVED_LABEL!>@Int<!>
        return<!UNRESOLVED_LABEL!>@p<!> 42
    }
    set(value) {
        this<!UNRESOLVED_LABEL!>@Int<!>
    }

class X {
    var Int.p: Int
        get() {
            this<!UNRESOLVED_LABEL!>@Int<!>
            return<!UNRESOLVED_LABEL!>@p<!> 42
        }
        set(value) {
            this<!UNRESOLVED_LABEL!>@Int<!>
        }

    fun Int.f() {
        this<!UNRESOLVED_LABEL!>@Int<!>
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, funWithExtensionReceiver, functionDeclaration, getter, integerLiteral,
propertyDeclaration, propertyWithExtensionReceiver, setter, thisExpression */
