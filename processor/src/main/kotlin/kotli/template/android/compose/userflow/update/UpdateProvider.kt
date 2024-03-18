package kotli.template.android.compose.userflow.update

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.update.google.GoogleUpdateProcessor

class UpdateProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.update"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GoogleUpdateProcessor()
    )

}