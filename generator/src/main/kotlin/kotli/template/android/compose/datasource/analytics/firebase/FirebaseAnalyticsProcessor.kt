package kotli.template.android.compose.datasource.analytics.firebase

import kotli.engine.FeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext
import kotli.template.android.compose.quality.crashes.firebase.FirebaseCrashlyticsProcessor
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseAnalyticsProcessor : FeatureProcessor() {

    private val appAnalytics = "app/src/main/kotlin/app/datasource/analytics/AppAnalyticsSource.kt"
    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override val id: String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/analytics"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/analytics/get-started"

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseCrashlyticsProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.apply(appAnalytics) {
            cleanupLine("{firebase-analytics}")
        }
        context.apply(appStartup) {
            cleanupLine("{firebase-analytics}")
        }
        context.apply("settings.gradle") {
            cleanupLine("{firebase-analytics}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{firebase-analytics}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply(appAnalytics) {
            removeLine("{firebase-analytics}")
        }
        context.apply(appStartup) {
            removeLine("{firebase-analytics}")
        }
        context.apply("settings.gradle") {
            removeLine("{firebase-analytics}")
        }
        context.apply("app/build.gradle") {
            removeLine("{firebase-analytics}")
        }
        context.apply("integration/firebase-analytics") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("firebase-analytics")
        }
    }

    companion object {
        const val ID = "firebase-analytics"
    }

}