package core.essentials.flow

interface IFlowStep<C : IFlowContext> {

    fun id(): String = javaClass.simpleName

    suspend fun proceed(context: C)

}