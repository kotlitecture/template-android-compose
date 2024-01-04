package core.datasource.work.worker

import core.datasource.work.Worker
import core.datasource.work.flow.IWorkFlowContext
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class HelloUserWorker : Worker<String, String>() {

    override val type: String = "hello_user"
    override val inputType: Class<String> = String::class.java
    override val outputType: Class<String> = String::class.java

    override suspend fun getName(input: String): String {
        return input
    }

    override suspend fun execute(context: IWorkFlowContext<String, String>): String {
        randomDelay()
        if (context.getInput() == ERROR_NAME) throw IllegalArgumentException("wrong user name")
        return "Hello ${context.getInput()} from ${UUID.randomUUID()}"
    }

    private suspend fun randomDelay() {
        delay(Random.nextLong(3, 5).seconds)
    }

    companion object {
        const val ERROR_NAME = "123"
    }

}