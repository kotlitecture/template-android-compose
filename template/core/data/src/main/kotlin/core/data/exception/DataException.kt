package core.data.exception

class DataException(
    val code: Int = 0,
    val msg: String
) : RuntimeException() {

    fun msg(): String = msg

    override fun toString(): String {
        return """
            Code: $code
            Message: $msg
        """.trimIndent()
    }

}