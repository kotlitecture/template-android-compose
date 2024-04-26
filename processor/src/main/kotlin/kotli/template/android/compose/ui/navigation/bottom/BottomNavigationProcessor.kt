package kotli.template.android.compose.ui.navigation.bottom

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AppActivityRules
import kotlin.time.Duration.Companion.hours

object BottomNavigationProcessor : BaseFeatureProcessor() {

    const val ID = "ui.navigation.bottom"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/bottom",
            RemoveFile()
        )
        state.onApplyRules(
            AppActivityRules(
                RemoveMarkedLine("BottomNavigation")
            )
        )
    }

}