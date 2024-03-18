package kotli.template.android.compose.userflow.passcode

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class PasscodeProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.passcode"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}