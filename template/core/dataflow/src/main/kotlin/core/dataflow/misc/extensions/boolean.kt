package core.dataflow.misc.extensions

fun Boolean?.orFalse(): Boolean = this ?: false