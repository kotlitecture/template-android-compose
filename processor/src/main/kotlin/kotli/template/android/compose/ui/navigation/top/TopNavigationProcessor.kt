package kotli.template.android.compose.ui.navigation.top

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.hours

class TopNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/top",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.navigation.top"
    }

}