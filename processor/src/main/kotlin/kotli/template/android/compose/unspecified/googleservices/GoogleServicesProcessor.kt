package kotli.template.android.compose.unspecified.googleservices

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceText
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class GoogleServicesProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getWebUrl(state: TemplateState): String = "https://cloud.google.com/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/android/setup"
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds
    override fun getConfigurationEstimate(state: TemplateState): Long = 30.minutes.inWholeMilliseconds

    override fun getConfiguration(state: TemplateState): String? {
        return super.getConfiguration(state)?.replace("kotli.app", state.layer.namespace)
    }

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{google-services}", true))
        state.onApplyRules("build.gradle", CleanupMarkedLine("{google-services}", true))
        state.onApplyRules("app/google-services.json", ReplaceText("kotli.app", state.layer.namespace))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{google-services}", true))
        state.onApplyRules("build.gradle", RemoveMarkedLine("{google-services}", true))
        state.onApplyRules("app/google-services.json", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("google-services")))
    }

    companion object {
        const val ID = "google-services"
    }

}