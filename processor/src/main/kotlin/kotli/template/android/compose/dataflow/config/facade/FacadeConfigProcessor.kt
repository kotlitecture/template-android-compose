package kotli.template.android.compose.dataflow.config.facade

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class FacadeConfigProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*ConfigSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.config.facade"
    }

}