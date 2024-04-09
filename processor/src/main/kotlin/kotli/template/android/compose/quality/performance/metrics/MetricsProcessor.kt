package kotli.template.android.compose.quality.performance.metrics

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.minutes

// WIP
object MetricsProcessor : BaseFeatureProcessor() {

    const val ID = "quality.performance.metrics"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/jetpack/androidx/releases/metrics"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/jetpack/androidx/releases/metrics#declaring_dependencies"
    override fun getIntegrationEstimate(state: TemplateState): Long = 10.minutes.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("androidxMetrics")
            )
        )
    }

}