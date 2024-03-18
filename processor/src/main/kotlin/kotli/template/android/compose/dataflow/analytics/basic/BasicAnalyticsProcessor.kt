package kotli.template.android.compose.dataflow.analytics.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicAnalyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*AnalyticsSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.analytics.basic"
    }

}