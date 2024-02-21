package kotli.template.android.compose.quality.performance.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebasePerformanceProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/perf-mon"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/perf-mon/get-started-android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{firebase-perf}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{firebase-perf}"))
        context.onApplyRule("build.gradle", CleanupMarkedLine("{firebase-perf}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{firebase-perf}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{firebase-perf}"))
        context.onApplyRule("build.gradle", RemoveMarkedLine("{firebase-perf}"))
        context.onApplyRule("integration/firebase-perf", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("firebase-perf"))
    }

    companion object {
        const val ID = "firebase-performance"
    }

}