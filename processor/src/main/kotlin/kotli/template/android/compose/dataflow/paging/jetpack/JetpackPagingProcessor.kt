package kotli.template.android.compose.dataflow.paging.jetpack

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.minutes

object JetpackPagingProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.paging.jetpack"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/topic/libraries/architecture/paging/v3-overview"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/topic/libraries/architecture/paging/v3-overview#setup"
    override fun getIntegrationEstimate(state: TemplateState): Long = 15.minutes.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("androidx-paging"),
                RemoveMarkedLine("androidxPaging")
            )
        )
    }

}