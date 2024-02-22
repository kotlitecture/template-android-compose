package kotli.template.android.compose.quality.performance.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebasePerformanceProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/perf-mon"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/perf-mon/get-started-android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{firebase-perf}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{firebase-perf}"))
        state.onApplyRules("build.gradle", CleanupMarkedLine("{firebase-perf}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{firebase-perf}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{firebase-perf}"))
        state.onApplyRules("build.gradle", RemoveMarkedLine("{firebase-perf}"))
        state.onApplyRules("integration/firebase-perf", RemoveFile())
        state.onApplyVersionCatalogRules(RemoveMarkedLine("firebase-perf"))
    }

    companion object {
        const val ID = "firebase-performance"
    }

}