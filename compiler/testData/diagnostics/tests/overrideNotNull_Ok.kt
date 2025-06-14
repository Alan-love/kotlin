// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// ISSUE: KT-57195

import java.io.File

open class AbstractFE1UastTest {
    open var testDataDir = File("").parentFile
}

class Legacy: AbstractFE1UastTest() {
    override var testDataDir: File? = File("").parentFile // K1 & K2: ok
}

/* GENERATED_FIR_TAGS: classDeclaration, flexibleType, javaFunction, javaProperty, nullableType, override,
propertyDeclaration, stringLiteral */
