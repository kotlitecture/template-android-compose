package core.data.flow

interface FlowGateway<C : FlowContext> : FlowStep<C> {

    val steps: List<FlowStep<C>>

}