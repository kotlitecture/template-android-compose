package kotli.template.android.compose.quality.performance.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor

class FirebasePerformanceProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String =
        "https://firebase.google.com/docs/perf-mon"

    override fun getIntegrationUrl(state: TemplateState): String =
        "https://firebase.google.com/docs/perf-mon/get-started-android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FirebaseProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("firebase.perf"),
            RemoveMarkedLine("firebase-perf"),
        )
        state.onApplyRules(
            "build.gradle",
            RemoveMarkedLine("firebase.perf")
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