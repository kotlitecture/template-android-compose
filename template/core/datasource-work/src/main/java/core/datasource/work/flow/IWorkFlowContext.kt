package core.datasource.work.flow

import core.datasource.work.IWorker
import core.datasource.work.model.WorkState

interface IWorkFlowContext<I, O> : IWorkFlow<I, O> {

    fun getWorker(): IWorker<I, O>

    suspend fun saveInput(input: I)

    suspend fun saveOutput(state: WorkState, output: O?)

    suspend fun saveState(state: WorkState, stateReason: String? = null)

    suspend fun <I, O, C : IWorker<I, O>> createChild(type: Class<C>, input: I): IWorkFlow<I, O>

    suspend fun setState(state: WorkState)

    suspend fun setName(name: String?)

}