// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// KT-439 Support labeled function literals in call arguments

fun main1(args : Array<String>) {
    run l@{ 1 } // should not be an error
}

/* GENERATED_FIR_TAGS: functionDeclaration, integerLiteral, lambdaLiteral */
