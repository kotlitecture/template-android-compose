package kotli.template.android.compose.dataflow.work

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class WorkProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("settings.gradle", CleanupMarkedLine("{datasource-work}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle", RemoveMarkedLine("{datasource-work}"))
        context.onApplyRules("core/datasource-work", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("androidxWork"))
    }

    companion object {
        const val ID = "work"
    }

}