// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// KT-394 Make companion object members visible inside the owning class

class X() {
//    class Y {}

    companion object{
        class Y() {}
    }

    val y : Y = Y()
}

/* GENERATED_FIR_TAGS: classDeclaration, companionObject, nestedClass, objectDeclaration, primaryConstructor,
propertyDeclaration */
