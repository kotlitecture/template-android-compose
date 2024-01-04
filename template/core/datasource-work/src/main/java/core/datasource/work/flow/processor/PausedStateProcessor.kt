package core.datasource.work.flow.processor

import core.datasource.work.flow.IWorkFlowContext

class PausedStateProcessor<I, O> : IStateProcessor<I, O> {

    override suspend fun proceed(context: IWorkFlowContext<I, O>) {}

}