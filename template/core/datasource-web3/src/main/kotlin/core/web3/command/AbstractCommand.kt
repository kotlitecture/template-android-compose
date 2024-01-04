package core.web3.command

import core.web3.IWeb3Context
import org.tinylog.Logger

abstract class AbstractCommand<R> : ICommand<R> {

    override suspend fun execute(context: IWeb3Context): R {
        try {
            val id = javaClass.simpleName
            Logger.debug("execute started :: id={}, context={}", id, context)
            val result = doExecute(context)
            Logger.debug("execute finished :: id={}, result={}", id, result)
            return result
        } catch (e: Exception) {
            Logger.error(e, "execute")
            throw CommandException(message = e.message, e = e)
        }
    }

    override suspend fun executeOptional(context: IWeb3Context): R? {
        return runCatching { execute(context) }.getOrNull()
    }

    protected abstract suspend fun doExecute(context: IWeb3Context): R

}