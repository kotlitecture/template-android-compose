package kotli.template.android.compose.transitive.googleservices

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceText

class GoogleServicesProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://cloud.google.com/"
    override fun getIntegrationUrl(state: TemplateState): String = "https://firebase.google.com/docs/android/setup"

    override fun getConfiguration(state: TemplateState): String? {
        return super.getConfiguration(state)?.replace("kotli.app", state.layer.namespace)
    }

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{google-services}"))
        state.onApplyRules("build.gradle", CleanupMarkedLine("{google-services}"))
        state.onApplyRules("app/google-services.json", ReplaceText("kotli.app") { state.layer.namespace })
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{google-services}"))
        state.onApplyRules("build.gradle", RemoveMarkedLine("{google-services}"))
        state.onApplyRules("app/google-services.json", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("google-services")))
    }

    companion object {
        const val ID = "google-services"
    }

}