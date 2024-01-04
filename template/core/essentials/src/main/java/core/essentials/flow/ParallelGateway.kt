package core.essentials.flow

import core.essentials.misc.extensions.withJobAsync
import kotlinx.coroutines.awaitAll

class ParallelGateway<C : IFlowContext>(vararg steps: IFlowStep<C>) : FlowGateway<C>() {

    override val steps: List<IFlowStep<C>> = steps.toList()

    override suspend fun doProceed(context: C) {
        steps.map { step -> withJobAsync { step.proceed(context) } }.awaitAll()
    }

}