// RUN_PIPELINE_TILL: FRONTEND
// ISSUE: KT-60450
fun main() {
    val l = listOf<String>()
    l.zip(l).forEach { left, <!CANNOT_INFER_VALUE_PARAMETER_TYPE!>right<!><!SYNTAX!><!>

    }
    l.zip(l).forEach <!ARGUMENT_TYPE_MISMATCH!>{ left, <!CANNOT_INFER_VALUE_PARAMETER_TYPE!>right<!> ->

    }<!>
}

/* GENERATED_FIR_TAGS: functionDeclaration, lambdaLiteral, localProperty, propertyDeclaration */
