package kotli.template.android.compose.dataflow.analytics.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.quality.crashes.firebase.FirebaseCrashlyticsProcessor
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseAnalyticsProcessor : BaseFeatureProcessor() {

    private val appAnalytics = "app/src/main/kotlin/app/datasource/analytics/AppAnalyticsSource.kt"
    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/analytics"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/analytics/get-started"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseCrashlyticsProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.onApplyRule(appAnalytics, CleanupMarkedLine("{firebase-analytics}"))
        context.onApplyRule(appStartup, CleanupMarkedLine("{firebase-analytics}"))
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{firebase-analytics}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{firebase-analytics}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule(appAnalytics, RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRule(appStartup, RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRule("integration/firebase-analytics", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("firebase-analytics"))
    }

    companion object {
        const val ID = "firebase-analytics"
    }

}