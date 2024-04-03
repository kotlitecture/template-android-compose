package kotli.template.android.compose.wip.flow

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

object FlowApiProcessor : BaseFeatureProcessor() {

    const val ID = "wip.flow"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data/src/main/kotlin/core/data/flow", RemoveFile())
    }

}