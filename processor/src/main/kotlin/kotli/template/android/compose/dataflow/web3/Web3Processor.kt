package kotli.template.android.compose.dataflow.web3

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class Web3Processor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{datasource-web3}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{datasource-web3}"))
        state.onApplyRules("core/datasource-web3", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("web3j")))
    }

    companion object {
        const val ID = "web3"
    }

}