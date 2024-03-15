package kotli.template.android.compose.dataflow.common

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class CommonDataFlowProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data", RemoveFile())
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{dataflow.common}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{dataflow.common}"))
    }

    companion object {
        const val ID = "dataflow.common"
    }

}