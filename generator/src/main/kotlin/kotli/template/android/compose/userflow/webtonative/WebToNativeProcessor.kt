package kotli.template.android.compose.userflow.webtonative

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class WebToNativeProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{workflow-webtonative}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{workflow-webtonative}"))
        context.onApplyRule("app/src/main/kotlin/app/AppActivity.kt", CleanupMarkedLine("{workflow-webtonative}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{workflow-webtonative}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{workflow-webtonative}"))
        context.onApplyRule("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("{workflow-webtonative}"))
        context.onApplyRule("app/src/main/kotlin/app/feature/webtonative", RemoveFile())
        context.onApplyRule("workflow/webtonative", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("androidxWebkit"))
    }

    companion object {
        const val ID = "webtonative"
    }

}