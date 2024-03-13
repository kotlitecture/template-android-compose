package core.data.flow

import org.tinylog.Logger
import kotlin.system.measureTimeMillis

abstract class BaseFlowStep<C : FlowContext> : FlowStep<C> {

    override suspend fun proceed(context: C) {
        if (context.isCanceled()) {
            log("skipped :: context={}", context)
            return
        }
        val duration = measureTimeMillis {
            try {
                log("started :: context={}", context)
                doProceed(context)
            } catch (e: Exception) {
                log("error :: {}", e.stackTraceToString())
                context.cancel()
            }
        }
        log("finished :: duration={}, context={}", duration, context)
    }

    protected fun log(message: String, vararg args: Any?) {
        Logger.info("[FLOW][${id()}] $message", *args)
    }

    protected abstract suspend fun doProceed(context: C)

}