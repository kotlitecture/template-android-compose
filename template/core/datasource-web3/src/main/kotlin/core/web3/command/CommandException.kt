package core.web3.command

class CommandException(
    message: String? = null,
    e: Exception? = null
) : RuntimeException(
    message,
    e
)