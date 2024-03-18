package core.data.flow

class SequentialGateway<C : FlowContext>(vararg steps: FlowStep<C>) : BaseFlowGateway<C>() {

    override val steps: List<FlowStep<C>> = steps.toList()

    override suspend fun doProceed(context: C) {
        steps.forEach { step -> step.proceed(context) }
    }

}