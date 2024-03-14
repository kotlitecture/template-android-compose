package kotli.template.android.compose.quality.crashes.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor

class FirebaseCrashlyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/crashlytics"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/crashlytics/get-started?platform=android"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("build.gradle", CleanupMarkedLine("{firebase-crashlytics}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules("build.gradle", RemoveMarkedLine("{firebase-crashlytics}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("firebase-crashlytics")))
    }

    companion object {
        const val ID = "firebase-crashlytics"
    }

}