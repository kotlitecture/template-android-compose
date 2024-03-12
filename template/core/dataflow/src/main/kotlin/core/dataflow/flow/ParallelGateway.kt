package core.dataflow.flow

import core.dataflow.misc.extensions.globalAsync
import kotlinx.coroutines.awaitAll

class ParallelGateway<C : FlowContext>(vararg steps: FlowStep<C>) : BaseFlowGateway<C>() {

    override val steps: List<FlowStep<C>> = steps.toList()

    override suspend fun doProceed(context: C) {
        steps.map { step -> globalAsync { step.proceed(context) } }.awaitAll()
    }

}