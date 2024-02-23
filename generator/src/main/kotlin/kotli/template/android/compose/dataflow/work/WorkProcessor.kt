package kotli.template.android.compose.dataflow.work

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class WorkProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{datasource-work}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{datasource-work}"))
        state.onApplyRules("core/datasource-work", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxWork")))
    }

    companion object {
        const val ID = "work"
    }

}