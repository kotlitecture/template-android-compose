package kotli.template.android.compose.dataflow.analytics.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
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
        context.onApplyRules(appAnalytics, CleanupMarkedLine("{firebase-analytics}"))
        context.onApplyRules(appStartup, CleanupMarkedLine("{firebase-analytics}"))
        context.onApplyRules("settings.gradle", CleanupMarkedLine("{firebase-analytics}"))
        context.onApplyRules("app/build.gradle", CleanupMarkedLine("{firebase-analytics}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules(appAnalytics, RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRules(appStartup, RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRules("settings.gradle", RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRules("app/build.gradle", RemoveMarkedLine("{firebase-analytics}"))
        context.onApplyRules("integration/firebase-analytics", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("firebase-analytics"))
    }

    companion object {
        const val ID = "firebase-analytics"
    }

}