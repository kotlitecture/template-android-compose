package kotli.template.android.compose.dataflow.storage.objectbox

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine

class ObjectBoxProcessor : BaseFeatureProcessor() {

    override fun getId(): String = "objectbox"

    override fun doApply(state: TemplateState) {
        state.onApplyRules("build.gradle", CleanupMarkedLine("{objectbox}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{objectbox}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("build.gradle", RemoveMarkedLine("{objectbox}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{objectbox}"))
        state.onApplyVersionCatalogRules(RemoveMarkedLine("objectbox"))
    }

}