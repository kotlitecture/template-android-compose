package core.datasource.work

import core.essentials.misc.extensions.withJobAsync
import core.essentials.misc.utils.GsonUtils.toObject
import core.essentials.misc.utils.GsonUtils.toString
import core.essentials.misc.utils.IdUtils
import kotlinx.coroutines.Deferred
import org.tinylog.Logger

abstract class Worker<I, O> : IWorker<I, O> {

    protected abstract val inputType: Class<I>
    protected abstract val outputType: Class<O>

    override fun createId(input: I): String = IdUtils.autoId()
    override suspend fun getName(input: I): String? = null
    override suspend fun getIcon(input: I): String? = null
    override suspend fun getDescription(input: I): String? = null
    override suspend fun storeInput(input: I): String = toString(input)!!
    override suspend fun storeOutput(output: O?): String? = toString(output)
    override suspend fun restoreInput(string: String): I = toObject(string, inputType)!!
    override suspend fun restoreOutput(string: String?): O? = toObject(string, outputType)

    protected suspend fun withDeferred(block: suspend () -> Unit): Deferred<Result<Any>> {
        val job = withJobAsync { block() }
        log("withDeferred :: job={}", job)
        job.invokeOnCompletion { log("withDeferred :: completed :: error={}", it) }
        return job
    }

    protected fun log(message: String, vararg args: Any?) {
        Logger.info("${javaClass.simpleName} :: $message", *args)
    }

}