package core.dataflow.flow

abstract class BaseFlowGateway<C : FlowContext> : BaseFlowStep<C>(), FlowGateway<C>