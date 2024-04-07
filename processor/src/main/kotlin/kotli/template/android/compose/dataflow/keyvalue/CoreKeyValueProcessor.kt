package kotli.template.android.compose.dataflow.keyvalue

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

object CoreKeyValueProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.keyvalue.core"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*/datasource/keyvalue/*", RemoveFile())
    }

}