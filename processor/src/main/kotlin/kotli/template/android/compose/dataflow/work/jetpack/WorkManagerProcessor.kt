package kotli.template.android.compose.dataflow.work.jetpack

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.minutes

class WorkManagerProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getIntegrationEstimate(state: TemplateState): Long = 30.minutes.inWholeMilliseconds
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/reference/androidx/work/WorkManager"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started"

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            VersionCatalogRules(
                listOf(
                    RemoveMarkedLine("androidxWork"),
                    RemoveMarkedLine("androidx-work")
                )
            )
        )
    }

    companion object {
        const val ID = "dataflow.work.jetpack"
    }

}