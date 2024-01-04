package core.essentials.flow

class SequentialGateway<C : IFlowContext>(vararg steps: IFlowStep<C>) : FlowGateway<C>() {

    override val steps: List<IFlowStep<C>> = steps.toList()

    override suspend fun doProceed(context: C) {
        steps.forEach { step -> step.proceed(context) }
    }

}