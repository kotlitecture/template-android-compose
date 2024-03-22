package kotli.template.android.compose.quality.performance.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor
import kotlin.time.Duration.Companion.hours

class FirebasePerformanceProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/perf-mon"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/perf-mon/get-started-android"
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{quality.performance.firebase}"),
            CleanupMarkedLine("{quality.performance.firebase}"),
        )
        state.onApplyRules(
            "build.gradle",
            CleanupMarkedLine("{quality.performance.firebase}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{quality.performance.firebase}"),
            RemoveMarkedLine("{quality.performance.firebase}"),
        )
        state.onApplyRules(
            "build.gradle",
            RemoveMarkedLine("{quality.performance.firebase}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("firebase-perf")
            )
        )
    }

    companion object {
        const val ID = "quality.performance.firebase"
    }

}