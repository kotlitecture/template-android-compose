package core.data.flow

abstract class BaseFlowGateway<C : FlowContext> : BaseFlowStep<C>(), FlowGateway<C>