// FIR_IDENTICAL
// IGNORE_BACKEND_K1: ANY
// LANGUAGE: +BreakContinueInInlineLambdas +IrInlinerBeforeKlibSerialization
// ISSUE: KT-68975
// See same test for codegen: compiler/testData/codegen/box/js/inlinedReturnBreakContinue/lambdaPassedToInlineFunction.kt

import kotlin.test.assertEquals

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
public annotation class SomeAnnotation

inline fun foo(block: () -> Unit) = js(<!JS_CODE_CAPTURES_INLINABLE_FUNCTION_WARNING!>"block()"<!>)

fun box(): String {
    val visited = mutableListOf<Pair<Int, Int>>()

    var i = 0
    outer@ while (true) {
        i += 1
        inner@ for (j in 1..10) {
            foo(
                (@SomeAnnotation
                fun() {
                    foo(fun() {
                        foo(@SomeAnnotation {
                            foo {
                                if (i == 2) {
                                    continue@outer
                                }

                                if (i == 4) {
                                    break@outer
                                }

                                if (j == 2) {
                                    continue
                                }

                                if (j == 4) {
                                    continue@inner
                                }

                                if (j == 6 && i == 1) {
                                    break@inner
                                }

                                if (j == 6 && i == 3) {
                                    break
                                }
                            }
                        })
                    })
                })
            )
            visited += i to j
        }
    }

    assertEquals(listOf(1 to 1, 1 to 3, 1 to 5, 3 to 1, 3 to 3, 3 to 5), visited)

    return "OK"
}
