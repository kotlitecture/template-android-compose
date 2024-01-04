package core.essentials.exception

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

    fun isInsufficientFunds(): Boolean = msg.contains("insufficient")

}