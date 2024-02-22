package kotli.template.android.compose.quality.crashes.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseCrashlyticsProcessor : BaseFeatureProcessor() {

    private val appStartup = "app/src/main/kotlin/app/AppStartupInitializer.kt"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/crashlytics"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/crashlytics/get-started?platform=android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(appStartup, CleanupMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(appStartup, RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("integration/firebase-crashlytics", RemoveFile())
        state.onApplyVersionCatalogRules(RemoveMarkedLine("firebase-crashlytics"))
    }

    companion object {
        const val ID = "firebase-crashlytics"
    }

}