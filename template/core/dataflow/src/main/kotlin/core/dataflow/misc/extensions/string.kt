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

fun String?.take(max: Int): String? {
    if (this == null) {
        return null
    }
    if (length <= max) {
        return this
    }
    val lastIndex = indexOfAny(arrayListOf(".", "\n\n"), max, true)
    if (lastIndex != -1 && lastIndex < length - 1) {
        return "${substring(0, lastIndex)}…"
    }
    return this
}