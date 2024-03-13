package core.data.flow

class Flow<C : FlowContext>(val id: String, vararg steps: FlowStep<C>) : BaseFlowGateway<C>() {

    override fun id(): String = id

    override val steps: List<FlowStep<C>> = steps.toList()

    override suspend fun doProceed(context: C) {
        steps.forEach { step -> step.proceed(context) }
    }

}