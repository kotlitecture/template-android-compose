package core.datasource.work.flow.processor

import core.datasource.work.flow.IWorkFlowContext
import core.datasource.work.model.WorkState
import core.essentials.misc.extensions.isCancellationException
import org.tinylog.Logger

class StartedStateProcessor<I, O> : IStateProcessor<I, O> {

    override suspend fun proceed(context: IWorkFlowContext<I, O>) {
        try {
            context.setState(WorkState.Processing)
            val worker = context.getWorker()
            val output = worker.execute(context)
            context.saveOutput(WorkState.Completed, output)
        } catch (e: Exception) {
            Logger.error(e, "started")
            if (!e.isCancellationException()) {
                context.saveState(WorkState.Error, e.message ?: e.stackTraceToString())
            }
        }
    }
}