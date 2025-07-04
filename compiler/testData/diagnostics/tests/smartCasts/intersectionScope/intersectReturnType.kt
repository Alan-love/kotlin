// RUN_PIPELINE_TILL: BACKEND
// SKIP_TXT
class C<T>(val value: T)

fun <T> assignable(x: () -> T) {}

fun <T, V> foo(t: C<out T>, v: C<out V>) {
    assignable<T> { t.value } // sure
    assignable<V> { v.value } // obviously
    if (t == v) {
        // => {t,v} is C<out T> & C<out V>
        // => {t,v}.value is T & V
        assignable<T> { <!TYPE_MISMATCH!>t.value<!> }
        assignable<V> { <!TYPE_MISMATCH!>v.value<!> }
        assignable<T> { v.value }
        assignable<V> { t.value }
    }
}

/* GENERATED_FIR_TAGS: capturedType, classDeclaration, equalityExpression, functionDeclaration, functionalType,
ifExpression, intersectionType, lambdaLiteral, nullableType, outProjection, primaryConstructor, propertyDeclaration,
smartcast, typeParameter */
