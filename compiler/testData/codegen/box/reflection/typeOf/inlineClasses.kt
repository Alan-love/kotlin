// IGNORE_BACKEND: JS_IR
// IGNORE_BACKEND: JS_IR_ES6
// WITH_REFLECT
// IGNORE_IR_DESERIALIZATION_TEST: JS_IR
// ^^^ Source code is not compiled in JS.
// WASM_ALLOW_FQNAME_IN_KCLASS

package test

import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.test.assertEquals

inline class Z(val value: String)

fun check(expected: String, actual: KType) {
    assertEquals(expected, actual.toString())
}

fun box(): String {
    check("test.Z", typeOf<Z>())
    check("test.Z?", typeOf<Z?>())
    check("kotlin.Array<test.Z>", typeOf<Array<Z>>())
    check("kotlin.Array<test.Z?>", typeOf<Array<Z?>>())

    check("kotlin.UInt", typeOf<UInt>())
    check("kotlin.UInt?", typeOf<UInt?>())
    check("kotlin.ULong?", typeOf<ULong?>())
    check("kotlin.UShortArray", typeOf<UShortArray>())
    check("kotlin.UShortArray?", typeOf<UShortArray?>())
    check("kotlin.Array<kotlin.UByteArray>", typeOf<Array<UByteArray>>())
    check("kotlin.Array<kotlin.UByteArray?>?", typeOf<Array<UByteArray?>?>())

    return "OK"
}
