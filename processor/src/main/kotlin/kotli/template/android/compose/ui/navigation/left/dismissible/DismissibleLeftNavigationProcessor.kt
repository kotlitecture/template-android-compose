package kotli.template.android.compose.ui.navigation.left.dismissible

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class DismissibleLeftNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/left/DismissibleLeftNavigation.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/NavigationProvider.kt",
            RemoveMarkedLine("DismissibleLeftNavigation")
        )
    }

    companion object {
        const val ID = "ui.navigation.left.dismissible"
    }

}