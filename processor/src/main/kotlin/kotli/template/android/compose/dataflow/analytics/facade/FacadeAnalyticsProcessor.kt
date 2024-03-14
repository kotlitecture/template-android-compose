package kotli.template.android.compose.dataflow.analytics.facade

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class FacadeAnalyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*AnalyticsSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.facade.analytics"
    }

}