package core.essentials.flow

interface IFlowGateway<C : IFlowContext> : IFlowStep<C> {

    val steps: List<IFlowStep<C>>

}