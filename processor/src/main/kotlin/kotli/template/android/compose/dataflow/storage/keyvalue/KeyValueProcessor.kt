package kotli.template.android.compose.dataflow.storage.keyvalue

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.hours

class KeyValueProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/training/data-storage/shared-preferences"
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*KeyValueSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.storage.key-value"
    }

}