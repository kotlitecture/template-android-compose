package kotli.template.android.compose.ui.navigation.bottom

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class BottomNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/bottom",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/AppActivity.kt",
            RemoveMarkedLine("BottomNavigation")
        )
    }

    companion object {
        const val ID = "ui.navigation.bottom"
    }

}