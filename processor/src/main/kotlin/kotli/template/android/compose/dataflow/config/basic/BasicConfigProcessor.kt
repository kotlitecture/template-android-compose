package kotli.template.android.compose.dataflow.config.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicConfigProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*ConfigSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.config.basic"
    }

}