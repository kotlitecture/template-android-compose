package core.essentials.flow

abstract class FlowContext : IFlowContext {

    private var canceled: Boolean = false

    override fun cancel() {
        canceled = true
    }

    override fun isCanceled(): Boolean {
        return canceled
    }

}