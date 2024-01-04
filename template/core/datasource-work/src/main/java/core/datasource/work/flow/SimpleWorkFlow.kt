package core.datasource.work.flow

import core.datasource.work.IWorkSource
import core.datasource.work.IWorker
import core.datasource.work.dao.IWorkDao
import core.datasource.work.flow.processor.CanceledStateProcessor
import core.datasource.work.flow.processor.CompletedStateProcessor
import core.datasource.work.flow.processor.CreatedStateProcessor
import core.datasource.work.flow.processor.ErrorStateProcessor
import core.datasource.work.flow.processor.IStateProcessor
import core.datasource.work.flow.processor.PausedStateProcessor
import core.datasource.work.flow.processor.ResumedStateProcessor
import core.datasource.work.flow.processor.StartedStateProcessor
import core.datasource.work.flow.processor.WaitingStateProcessor
import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkModel
import core.datasource.work.model.WorkState
import core.data.state.StoreObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import org.tinylog.Logger
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

internal class SimpleWorkFlow<I, O>(
    model: WorkModel,
    private val input: I?,
    private val dao: IWorkDao,
    private val worker: IWorker<I, O>,
    private val workSource: IWorkSource
) : IWorkFlow<I, O>, IWorkFlowContext<I, O> {

    private var work = model
    private val dataStore = StoreObject<Any>()
    private val modelStore = StoreObject(model)
    private val started = AtomicBoolean(false)
    private var executionContext: CoroutineContext? = null

    private val processors = mapOf<WorkState, IStateProcessor<I, O>>(
        WorkState.Created to CreatedStateProcessor(),
        WorkState.Started to StartedStateProcessor(),
        WorkState.Processing to StartedStateProcessor(),
        WorkState.Preparing to StartedStateProcessor(),
        WorkState.Waiting to WaitingStateProcessor(),
        WorkState.Paused to PausedStateProcessor(),
        WorkState.Resumed to ResumedStateProcessor(),
        WorkState.Completed to CompletedStateProcessor(),
        WorkState.Canceled to CanceledStateProcessor(),
        WorkState.Error to ErrorStateProcessor()
    )

    override fun getId(): String = work.uid
    override fun getType(): String = worker.type
    override fun getWorker(): IWorker<I, O> = worker
    override fun getParentId(): String? = work.parentId
    override fun getDataStore(): StoreObject<Any> = dataStore
    override fun getModelStore(): StoreObject<WorkModel> = modelStore
    override fun getState(): WorkState = modelStore.getNotNull().state
    override fun getStateReason(): String? = modelStore.getNotNull().stateReason
    override suspend fun getInput(): I = input ?: worker.restoreInput(work.input)
    override suspend fun getOutput(): O? = worker.restoreOutput(work.output)
    override suspend fun awaitOutput(): O? {
        return modelStore.asFlow()
            .filterNotNull()
            .filter { it.state.terminal }
            .map { worker.restoreOutput(it.output) }
            .first()
    }

    override suspend fun cancel(state: WorkState) {
        Logger.debug(
            "cancel work :: [id={}, name={}, state={}] -> state={}",
            work.uid,
            work.name,
            work.state,
            state
        )
        executionContext?.cancel()
        executionContext = null
        val currentState = work.state
        if (currentState != state && !currentState.terminal) saveState(state)
        getChildren().forEach { it.cancel(state) }
    }

    override suspend fun resume() {
        if (work.state.terminal) return
        Logger.debug("resume work :: id={}, state={}, input={}", work.uid, work.state, work.input)
        if (work.state == WorkState.Paused) saveState(WorkState.Resumed)
        workSource.enqueue(this)
    }

    override suspend fun saveInput(input: I) {
        val updated = work.copy(
            input = worker.storeInput(input)
        )
        work = dao.save(updated)
        modelStore.set(work)
    }

    override suspend fun saveOutput(state: WorkState, output: O?) {
        val updated = work.copy(
            output = worker.storeOutput(output),
            state = state,
        )
        work = dao.save(updated)
        modelStore.set(work)
    }

    override suspend fun saveState(state: WorkState, stateReason: String?) {
        val updated = work.copy(
            stateReason = stateReason,
            state = state,
        )
        work = dao.save(updated)
        modelStore.set(work)
    }

    override suspend fun setName(name: String?) {
        modelStore.set(modelStore.getNotNull().copy(name = name))
    }

    override suspend fun setState(state: WorkState) {
        modelStore.set(modelStore.getNotNull().copy(state = state, stateReason = null))
    }

    override suspend fun execute(): O? {
        if (started.compareAndSet(false, true)) {
            try {
                executionContext = coroutineContext
                while (coroutineContext.isActive) {
                    val workState = work.state
                    val processor = processors[workState]
                        ?: throw IllegalStateException("state is unknown : $workState")
                    Logger.debug(
                        "proceed state :: worker={}, id={}, state={}, processor={}",
                        work.type,
                        work.uid,
                        workState,
                        processor
                    )
                    processor.proceed(this)
                    if (workState == work.state) {
                        break
                    }
                }
                return getOutput()
            } catch (e: Exception) {
                Logger.error(e, "execute")
                throw e
            } finally {
                started.set(false)
            }
        } else {
            return awaitOutput()
        }
    }

    override suspend fun <I, O, C : IWorker<I, O>> createChild(
        type: Class<C>,
        input: I
    ): IWorkFlow<I, O> {
        return workSource.create(type, input, work.uid)
    }

    override suspend fun getChildren(): List<IWorkFlow<*, *>> {
        val filter = WorkFilter(
            sortBy = WorkFilter.SORT_BY_CREATE_DATE_ASC,
            stateIn = emptyList(),
            parentId = work.uid,
        )
        return workSource.getAll(filter).first()
    }

}