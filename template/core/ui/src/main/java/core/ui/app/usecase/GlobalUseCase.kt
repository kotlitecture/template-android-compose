package core.ui.app.usecase

import core.essentials.misc.extensions.launchGlobalAsync
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.tinylog.Logger
import java.util.concurrent.atomic.AtomicReference

abstract class GlobalUseCase<IN : Any, OUT : Any> : UseCase<IN, OUT>() {

    protected abstract val id: String

    private val lastContext = AtomicReference<Context<IN, OUT>>()
    private val semaphore = Semaphore(1)

    override suspend fun proceed(input: IN): Context<IN, OUT> {
        return semaphore.withPermit {
            val id = "global_use_case_$id"
            val last = lastContext.get()
            if (last == null || last.input != input || !last.isActive()) {
                Logger.debug("create global task :: {}", input)
                last?.cancel()
                val newContext = Context<IN, OUT>(input = input)
                launchGlobalAsync(
                    id = id,
                    onReady = newContext::addJob,
                    block = { doProceed(newContext) }
                )
                lastContext.set(newContext)
                newContext
            } else {
                Logger.debug("skip global task :: {}", input)
                last
            }
        }
    }

}