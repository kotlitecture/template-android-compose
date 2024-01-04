package core.ui.app.usecase

import core.essentials.misc.extensions.withJobAsync
import kotlinx.coroutines.Deferred
import org.tinylog.Logger
import java.util.Date
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

abstract class UseCase<IN : Any, OUT : Any> {

    open suspend fun proceed(input: IN): Context<IN, OUT> {
        val context = Context<IN, OUT>(input = input)
        Logger.debug("proceed :: request={}", input)
        doProceed(context)
        Logger.debug("proceed :: response={}", context.output)
        return context
    }

    protected suspend fun withDeferred(context: Context<IN, OUT>, block: suspend () -> Unit) {
        val job = withJobAsync { block() }
        context.addJob(job)
    }

    protected abstract suspend fun doProceed(context: Context<IN, OUT>)

    data class Context<IN, OUT>(
        val input: IN,
        var output: OUT? = null,
        val createDate: Date = Date(),
        internal val jobs: Queue<Deferred<Any>> = ConcurrentLinkedQueue()
    ) {
        fun addJob(job: Deferred<Any>) {
            jobs.add(job)
        }

        fun isActive(): Boolean {
            return jobs.any { it.isActive }
        }

        fun cancel() {
            jobs.forEach { it.cancel() }
        }

        suspend fun await(): OUT? {
            jobs.forEach { runCatching { it.await() } }
            return output
        }

        suspend fun awaitNotNull(): OUT {
            return await()!!
        }
    }

    protected fun log(message: String, vararg args: Any?) {
        Logger.info("${javaClass.simpleName} :: $message", *args)
    }

}