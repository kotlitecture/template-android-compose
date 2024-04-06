package kotli.template.android.compose.dataflow.keyvalue.datastore

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.dataflow.keyvalue.AppKeyValueProcessor
import kotlin.time.Duration.Companion.hours

object DataStoreProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.keyvalue.datastore"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/topic/libraries/architecture/datastore"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/topic/libraries/architecture/datastore#setup"
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        AppKeyValueProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/datasource/keyvalue/AppKeyValueSource.kt",
            ReplaceMarkedText(
                text = "SharedPreferencesSource",
                marker = "SharedPreferencesSource",
                replacer = "SharedPreferencesDataStore"
            )
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*/SharedPreferencesDataStore*", RemoveFile())

        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("androidx-datastore"),
                RemoveMarkedLine("androidxDatastore"),
            )
        )
    }

}