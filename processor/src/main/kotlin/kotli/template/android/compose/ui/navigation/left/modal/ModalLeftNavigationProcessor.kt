package kotli.template.android.compose.ui.navigation.left.modal

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class ModalLeftNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/left/ModalLeftNavigation.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/NavigationBarProvider.kt",
            RemoveMarkedLine("ModalLeftNavigation")
        )
    }

    companion object {
        const val ID = "ui.navigation.left.modal"
    }

}