package core.data.flow

abstract class BaseFlowStep<C : FlowContext> : FlowStep<C> {

    override suspend fun proceed(context: C) {
        if (context.isCanceled()) {
            return
        }
        try {
            doProceed(context)
        } catch (e: Exception) {
            context.cancel()
        }
    }

    protected abstract suspend fun doProceed(context: C)

}