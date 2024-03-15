package kotli.template.android.compose.userflow.webtonative

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.webtonative.basic.WebToNativeProcessor

class WebToNativeProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.webtonative"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        WebToNativeProcessor()
    )

}