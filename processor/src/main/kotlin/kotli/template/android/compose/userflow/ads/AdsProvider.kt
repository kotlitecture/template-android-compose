package kotli.template.android.compose.userflow.ads

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class AdsProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.ads"
    override fun isMultiple(): Boolean = true
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}