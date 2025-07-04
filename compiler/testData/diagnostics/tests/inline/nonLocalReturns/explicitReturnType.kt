// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
fun inlineCallExplicitError(): String {
    inlineFun lamba@ {
        if (true) {
            return@lamba 2
        }
        1
    }

    return "x"
}

fun inlineCall(): String {
    inlineFun lamba@ {
        if (true) {
            return@lamba 2
        }
        1
    }

    return "x"
}

inline fun inlineFun(s: () -> Int) {
    s()
}


fun noInlineCall(): String {
    noInline lambda@ {
        if (true) {
            return@lambda 2
        }
        1
    }
    return "x"
}


fun noInline(s: () -> Int) {
    s()
}

/* GENERATED_FIR_TAGS: functionDeclaration, functionalType, ifExpression, inline, integerLiteral, lambdaLiteral,
stringLiteral */
