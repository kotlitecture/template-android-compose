package kotli.template.android.compose.dataflow.network.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicNetworkProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*NetworkSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.network.basic"
    }

}