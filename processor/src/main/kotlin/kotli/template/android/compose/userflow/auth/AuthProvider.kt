package kotli.template.android.compose.userflow.auth

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.auth.google.GoogleAuthProcessor

class AuthProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.auth"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GoogleAuthProcessor()
    )

}