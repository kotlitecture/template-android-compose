package core.datasource.work.flow.processor

import core.datasource.work.flow.IWorkFlowContext
import core.datasource.work.model.WorkState

class ResumedStateProcessor<I, O> : IStateProcessor<I, O> {

    override suspend fun proceed(context: IWorkFlowContext<I, O>) {
        context.saveState(WorkState.Started)
    }

}