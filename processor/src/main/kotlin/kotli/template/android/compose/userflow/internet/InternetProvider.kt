package kotli.template.android.compose.userflow.internet

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.internet.no.NoInternetProcessor

class InternetProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.internet"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        NoInternetProcessor()
    )

}