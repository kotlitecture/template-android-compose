package core.essentials.flow

class Flow<C : IFlowContext>(val id: String, vararg steps: IFlowStep<C>) : FlowGateway<C>() {

    override fun id(): String = id

    override val steps: List<IFlowStep<C>> = steps.toList()

    override suspend fun doProceed(context: C) {
        steps.forEach { step -> step.proceed(context) }
    }

}