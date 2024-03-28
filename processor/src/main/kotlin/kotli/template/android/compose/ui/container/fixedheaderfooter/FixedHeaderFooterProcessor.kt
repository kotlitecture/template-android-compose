package kotli.template.android.compose.ui.container.fixedheaderfooter

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.hours

class FixedHeaderFooterProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/container/FixedHeaderFooterLayout.kt",
            RemoveFile()
        )
    }

    companion object {
        const val ID = "ui.container.fixedheaderfooter"
    }

}