package core.dataflow.misc.extensions

fun String.rangeBetween(prefix: String, suffix: String, offset: Int = 0): IntRange? {
    val start = indexOf(prefix, offset).takeIfIndex()?.plus(prefix.length)
    if (start != null) {
        val end = indexOf(suffix, start).takeIfIndex()
        if (end != null) {
            return IntRange(start, end)
        }
    }
    return null
}

fun String.ifNotEmpty(): String? = this.takeIf { it.isNotEmpty() }

fun String.inBrackets(): String = "($this)"

fun String.trimLines(): String = lines().joinToString("\n") { it.trim() }