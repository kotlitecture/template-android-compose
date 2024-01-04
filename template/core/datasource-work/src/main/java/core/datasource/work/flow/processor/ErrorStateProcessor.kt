package core.datasource.work.flow.processor

import core.datasource.work.flow.IWorkFlowContext

class ErrorStateProcessor<I, O> : IStateProcessor<I, O> {

    override suspend fun proceed(context: IWorkFlowContext<I, O>) {}

}