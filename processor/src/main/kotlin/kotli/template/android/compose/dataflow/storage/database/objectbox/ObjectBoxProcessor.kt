package kotli.template.android.compose.dataflow.storage.database.objectbox

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine

object ObjectBoxProcessor : BaseFeatureProcessor() {

    const val ID = "dataflow.storage.database.objectbox"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun doApply(state: TemplateState) {
        state.onApplyRules("build.gradle", CleanupMarkedLine("{dataflow.storage.database.objectbox}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{dataflow.storage.database.objectbox}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("build.gradle", RemoveMarkedLine("{dataflow.storage.database.objectbox}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{dataflow.storage.database.objectbox}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("objectbox")))
    }

}