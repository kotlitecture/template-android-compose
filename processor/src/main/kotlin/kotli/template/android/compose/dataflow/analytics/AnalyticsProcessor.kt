package kotli.template.android.compose.dataflow.analytics

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class AnalyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*AnalyticsSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.analytics"
    }

}