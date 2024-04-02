package kotli.template.android.compose.dataflow.storage.keyvalue

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

object CoreKeyValueProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.storage.keyvalue.core"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*/datasource/storage/keyvalue/*", RemoveFile())
    }

}