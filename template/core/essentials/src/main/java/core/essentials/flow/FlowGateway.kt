package core.essentials.flow

abstract class FlowGateway<C : IFlowContext> : FlowStep<C>(), IFlowGateway<C>