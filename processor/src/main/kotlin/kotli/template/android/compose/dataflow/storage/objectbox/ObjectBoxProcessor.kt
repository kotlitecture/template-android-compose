package kotli.template.android.compose.dataflow.storage.objectbox

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine

class ObjectBoxProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doApply(state: TemplateState) {
        state.onApplyRules("build.gradle", CleanupMarkedLine("{dataflow.storage.objectbox}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{dataflow.storage.objectbox}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("build.gradle", RemoveMarkedLine("{dataflow.storage.objectbox}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{dataflow.storage.objectbox}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("objectbox")))
    }

    companion object {
        const val ID = "dataflow.storage.objectbox"
    }

}