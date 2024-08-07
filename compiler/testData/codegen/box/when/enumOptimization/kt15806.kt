// JVM_ABI_K1_K2_DIFF: KT-62903

private fun Any?.doTheThing(): String {
    when (this) {
        is String -> return this
        is Level -> {
            when (this) {
                Level.O -> return Level.O.name
                Level.K -> return Level.K.name
            }
        }

        else -> return "fail"
    }
}


enum class Level {
    O,
    K
}


fun box(): String {
    return "O".doTheThing() + Level.K.doTheThing()
}
