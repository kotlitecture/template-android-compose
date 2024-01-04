package core.datasource.work.flow.processor

import core.datasource.work.flow.IWorkFlowContext

interface IStateProcessor<I, O> {
    suspend fun proceed(context: IWorkFlowContext<I, O>)
}