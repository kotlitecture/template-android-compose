package kotli.template.android.compose.userflow.auth

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class AuthProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.auth"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}