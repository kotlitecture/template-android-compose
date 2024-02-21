package kotli.template.android.compose.transitive.googleservices

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceText

class GoogleServicesProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://cloud.google.com/"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/android/setup"

    override fun getConfiguration(context: TemplateContext): String? {
        return super.getConfiguration(context)?.replace("kotli.app", context.layer.namespace)
    }

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("app/build.gradle", CleanupMarkedLine("{google-services}"))
        context.onApplyRules("build.gradle", CleanupMarkedLine("{google-services}"))
        context.onApplyRules("app/google-services.json", ReplaceText("kotli.app") { context.layer.namespace })
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("app/build.gradle", RemoveMarkedLine("{google-services}"))
        context.onApplyRules("build.gradle", RemoveMarkedLine("{google-services}"))
        context.onApplyRules("app/google-services.json", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("google-services"))
    }

    companion object {
        const val ID = "google-services"
    }

}