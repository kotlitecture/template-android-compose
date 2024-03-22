package kotli.template.android.compose.dataflow.analytics.facade

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.hours

class FacadeAnalyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*AnalyticsSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.analytics.facade"
    }

}