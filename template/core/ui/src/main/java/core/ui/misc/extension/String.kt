package core.ui.misc.extension

fun String?.cutString(max: Int = 20): String? {
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