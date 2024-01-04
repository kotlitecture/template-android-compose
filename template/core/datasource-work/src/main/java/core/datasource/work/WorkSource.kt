@file:Suppress("UNCHECKED_CAST")

package core.datasource.work

import android.app.Application
import core.datasource.work.dao.IWorkDao
import core.datasource.work.flow.IWorkFlow
import core.datasource.work.flow.IWorkFlowContext
import core.datasource.work.flow.SimpleWorkFlow
import core.datasource.work.foreground.ForegroundWorkService
import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkModel
import core.datasource.work.model.WorkState
import core.essentials.exception.DataException
import core.essentials.cache.ICacheSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.tinylog.Logger

class WorkSource(
    workers: List<IWorker<*, *>>,
    private val cacheSource: ICacheSource,
    private val app: Application,
    private val dao: IWorkDao,
) : IWorkSource {

    private val queue = MutableSharedFlow<IWorkFlow<*, *>>(1, extraBufferCapacity = Int.MAX_VALUE)
    private val workersByClass: Map<Class<*>, IWorker<*, *>> = workers.associateBy { it.javaClass }
    private val workersByType: Map<String, IWorker<*, *>> = workers.associateBy { it.type }

    override suspend fun start() {
        ForegroundWorkService.start(app)
    }

    override suspend fun queue(): Flow<IWorkFlow<*, *>> {
        return queue
            .filterNotNull()
            .filter { WorkState.Pending.contains(it.getState()) }
    }

    override suspend fun update(work: IWork<*>, state: WorkState, reason: String?) {
        val context = work as IWorkFlowContext<*, *>
        val flow = work as IWorkFlow<*, *>
        context.saveState(state, reason)
        Logger.debug("update state :: {} - {}", state, work.getId())
        if (!WorkState.Pending.contains(state)) {
            flow.cancel(state)
        }
        ForegroundWorkService.start(app)
        queue.tryEmit(flow)
    }

    override suspend fun getAll(filter: WorkFilter): Flow<List<IWorkFlow<*, *>>> {
        return dao.getAll(filter)
            .map { all ->
                all.mapNotNull { work ->
                    val worker = workersByType[work.type]!!
                    cacheSource.get(IWorkFlow.CacheKey(work.uid)) {
                        SimpleWorkFlow(
                            workSource = this,
                            worker = worker,
                            model = work,
                            input = null,
                            dao = dao
                        )
                    }
                }
            }
    }

    override suspend fun <I, O, C : IWorker<I, O>> create(
        type: Class<C>,
        input: I,
        parentId: String?
    ): IWorkFlow<I, O> {
        val worker = workersByClass[type] as? IWorker<I, O>
            ?: throw DataException(msg = "Unknown worker $type")
        val uid = worker.createId(input)
        val key = IWorkFlow.CacheKey<IWorkFlow<I, O>>(uid)
        cacheSource.remove(key)
        val flow = cacheSource.get(key) {
            val work = WorkModel(
                uid = uid,
                type = worker.type,
                parentId = parentId,
                name = worker.getName(input),
                icon = worker.getIcon(input),
                input = worker.storeInput(input),
                description = worker.getDescription(input)
            )
            dao.save(work)

            val flow = SimpleWorkFlow(
                workSource = this,
                worker = worker,
                input = input,
                model = work,
                dao = dao
            )

            flow
        }!!

        enqueue(flow)

        return flow
    }

    override suspend fun enqueue(flow: IWorkFlow<*, *>) {
        start()
        queue.tryEmit(flow)
    }

}