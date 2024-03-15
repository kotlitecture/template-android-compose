package kotli.template.android.compose.userflow.support

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class SupportProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.support"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}