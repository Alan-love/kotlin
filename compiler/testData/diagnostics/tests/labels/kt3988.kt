// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
//KT-3988 This@label for outer function not resolved

class Comment() {
    var article = ""

}
class Comment2() {
    var article2 = ""
}

fun new(body: Comment.() -> Unit) = body

fun new2(body: Comment2.() -> Unit) = body

fun main() {
    new {
        new2 {
            this@new //UNRESOLVED REFERENCE
        }
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, functionalType, lambdaLiteral, primaryConstructor,
propertyDeclaration, stringLiteral, thisExpression, typeWithExtension */
