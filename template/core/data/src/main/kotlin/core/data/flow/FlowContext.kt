package core.data.flow

interface FlowContext {

    fun cancel()

    fun isCanceled(): Boolean

}