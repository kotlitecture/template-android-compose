package core.datasource.work

import core.datasource.work.flow.IWorkFlowContext

interface IWorker<I, O> {

    val type: String

    fun createId(input: I): String

    suspend fun getIcon(input: I): String?

    suspend fun getName(input: I): String?

    suspend fun getDescription(input: I): String?

    suspend fun storeInput(input: I): String

    suspend fun restoreInput(string: String): I

    suspend fun storeOutput(output: O?): String?

    suspend fun restoreOutput(string: String?): O?

    suspend fun execute(context: IWorkFlowContext<I, O>): O?

}