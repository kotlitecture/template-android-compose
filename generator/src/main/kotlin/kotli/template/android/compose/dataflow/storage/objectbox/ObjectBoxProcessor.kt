package kotli.template.android.compose.dataflow.storage.objectbox

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine

class ObjectBoxProcessor : BaseFeatureProcessor() {

    override fun getId(): String = "objectbox"

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("build.gradle", CleanupMarkedLine("{objectbox}"))
        context.onApplyRules("app/build.gradle", CleanupMarkedLine("{objectbox}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("build.gradle", RemoveMarkedLine("{objectbox}"))
        context.onApplyRules("app/build.gradle", RemoveMarkedLine("{objectbox}"))
        context.onApplyVersionCatalogRules(RemoveMarkedLine("objectbox"))
    }

}