package core.essentials.flow

interface IFlowContext {

    fun cancel()

    fun isCanceled(): Boolean

}