package kotli.template.android.compose.userflow.theme

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider
import kotli.template.android.compose.userflow.theme.change.ChangeThemeProcessor
import kotli.template.android.compose.userflow.theme.toggle.ToggleThemeProcessor

object ThemeProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.theme"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ChangeThemeProcessor,
        ToggleThemeProcessor
    )

}