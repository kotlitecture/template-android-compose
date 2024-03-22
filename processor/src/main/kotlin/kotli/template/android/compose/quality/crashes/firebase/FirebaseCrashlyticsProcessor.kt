package kotli.template.android.compose.quality.crashes.firebase

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor
import kotlin.time.Duration.Companion.hours

class FirebaseCrashlyticsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://firebase.google.com/docs/crashlytics"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/crashlytics/get-started?platform=android"
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        FirebaseProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{quality.crashes.firebase}"))
        state.onApplyRules("build.gradle", CleanupMarkedLine("{quality.crashes.firebase}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{quality.crashes.firebase}"))
        state.onApplyRules("build.gradle", RemoveMarkedLine("{quality.crashes.firebase}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("firebase-crashlytics")))
    }

    companion object {
        const val ID = "quality.crashes.firebase"
    }

}