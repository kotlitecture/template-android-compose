package core.datasource.work.worker

import core.datasource.work.Worker
import core.datasource.work.flow.IWorkFlowContext

class HelloUserGroupWorker : Worker<HelloUserGroupWorker.Input, HelloUserGroupWorker.Output>() {

    override val type: String = "hello_user_group"
    override val inputType: Class<Input> = Input::class.java
    override val outputType: Class<Output> = Output::class.java

    override suspend fun execute(context: IWorkFlowContext<Input, Output>): Output {
        val children = context.getChildren().takeIf { it.isNotEmpty() } ?: run {
            context.getInput().list.map { context.createChild(HelloUserWorker::class.java, it) }
        }
        return children
            .onEach { it.resume() }
            .map { it.awaitOutput() }
            .map { it as? String }
            .let { Output(it) }
    }

    data class Input(
        val list: List<String>
    )

    data class Output(
        val list: List<String?>
    )

}