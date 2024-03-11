package core.dataflow.flow

abstract class BaseFlowContext : FlowContext {

    private var canceled: Boolean = false

    override fun cancel() {
        canceled = true
    }

    override fun isCanceled(): Boolean {
        return canceled
    }

}