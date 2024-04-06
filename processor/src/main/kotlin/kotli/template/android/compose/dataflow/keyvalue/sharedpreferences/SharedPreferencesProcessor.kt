package kotli.template.android.compose.dataflow.keyvalue.sharedpreferences

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.template.android.compose.dataflow.keyvalue.AppKeyValueProcessor
import kotli.template.android.compose.dataflow.keyvalue.CoreSharedPreferencesKeyValueProcessor
import kotlin.time.Duration.Companion.hours

object SharedPreferencesProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.keyvalue.sharedpreferences"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/training/data-storage/shared-preferences"
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        CoreSharedPreferencesKeyValueProcessor::class.java,
        AppKeyValueProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*/SharedPreferencesSource*", RemoveFile())
    }

}