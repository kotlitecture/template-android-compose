package kotli.template.android.compose.userflow.passcode

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.passcode.local.LocalPasscodeProcessor

object PasscodeProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.passcode"
    override fun isMultiple(): Boolean = false
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        LocalPasscodeProcessor
    )

}