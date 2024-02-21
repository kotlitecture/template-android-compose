package kotli.template.android.compose.dataflow.storage.objectbox

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine

class ObjectBoxProcessor : BaseFeatureProcessor() {

    override fun getId(): String = "objectbox"

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("build.gradle", CleanupMarkedLine("{objectbox}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{objectbox}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("build.gradle", RemoveMarkedLine("{objectbox}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{objectbox}"))
        context.onAddVersionCatalogRules(RemoveMarkedLine("objectbox"))
    }

}