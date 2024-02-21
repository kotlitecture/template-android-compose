package kotli.template.android.compose.dataflow.web3

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class Web3Processor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("settings.gradle", CleanupMarkedLine("{datasource-web3}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle", RemoveMarkedLine("{datasource-web3}"))
        context.onApplyRules("core/datasource-web3", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("web3j"))
    }

    companion object {
        const val ID = "web3"
    }

}