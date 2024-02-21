package kotli.template.android.compose.quality.crashes.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseCrashlyticsProcessor : BaseFeatureProcessor() {

    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/crashlytics"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/crashlytics/get-started?platform=android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.onApplyRule(appStartup, CleanupMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule(appStartup, RemoveMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        context.onApplyRule("integration/firebase-crashlytics", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("firebase-crashlytics"))
    }

    companion object {
        const val ID = "firebase-crashlytics"
    }

}