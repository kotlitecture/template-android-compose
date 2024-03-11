package core.dataflow.flow

interface FlowContext {

    fun cancel()

    fun isCanceled(): Boolean

}