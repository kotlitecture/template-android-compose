package core.datasource.work

import core.datasource.IDataSource
import core.datasource.work.flow.IWorkFlow
import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkState
import kotlinx.coroutines.flow.Flow

interface IWorkSource : IDataSource {

    suspend fun start()

    suspend fun queue(): Flow<IWorkFlow<*, *>>

    suspend fun enqueue(flow: IWorkFlow<*, *>)

    suspend fun getAll(filter: WorkFilter): Flow<List<IWorkFlow<*, *>>>

    suspend fun update(work: IWork<*>, state: WorkState, reason: String?)

    suspend fun <I, O, C : IWorker<I, O>> create(
        type: Class<C>,
        input: I,
        parentId: String? = null
    ): IWorkFlow<I, O>

}