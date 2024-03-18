package kotli.template.android.compose.dataflow.clipboard.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicClipboardProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*ClipboardSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.clipboard.basic"
    }

}