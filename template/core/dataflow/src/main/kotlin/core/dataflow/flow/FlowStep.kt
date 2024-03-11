package core.dataflow.flow

interface FlowStep<C : FlowContext> {

    fun id(): String = javaClass.simpleName

    suspend fun proceed(context: C)

}