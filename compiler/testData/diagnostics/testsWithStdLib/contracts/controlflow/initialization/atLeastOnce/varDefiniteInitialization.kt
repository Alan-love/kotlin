// RUN_PIPELINE_TILL: FRONTEND
// FIR_IDENTICAL
// LANGUAGE: +AllowContractsForCustomFunctions +UseCallsInPlaceEffect
// OPT_IN: kotlin.contracts.ExperimentalContracts
// DIAGNOSTICS: -INVISIBLE_REFERENCE -INVISIBLE_MEMBER

import kotlin.contracts.*

fun <T> runTwice(block: () -> T): T {
    contract {
        callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
    }
    block()
    return block();
};

fun testInitialization() {
    var x: Int
    <!UNINITIALIZED_VARIABLE!>x<!>.inc()
    runTwice { x = 42 }
    x.inc()
    x = 43
    x.inc()
}

fun repeatingFlow(n: Int) {
    var x: Int
    for (i in 1..n) {
        runTwice { x = 42 }
    }
    <!UNINITIALIZED_VARIABLE!>x<!>.inc()
}

/* GENERATED_FIR_TAGS: assignment, contractCallsEffect, contracts, forLoop, functionDeclaration, functionalType,
integerLiteral, lambdaLiteral, localProperty, nullableType, propertyDeclaration, rangeExpression, typeParameter */
