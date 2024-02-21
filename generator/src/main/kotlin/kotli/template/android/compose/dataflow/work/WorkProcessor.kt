package kotli.template.android.compose.dataflow.work

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class WorkProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{datasource-work}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{datasource-work}"))
        context.onApplyRule("core/datasource-work", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("androidxWork"))
    }

    companion object {
        const val ID = "work"
    }

}