package kotli.template.android.compose.userflow.kyc

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class KycProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.kyc"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}