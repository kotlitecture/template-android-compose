package core.data.misc.extensions

fun Boolean?.orFalse(): Boolean = this ?: false