package kotli.template.android.compose.dataflow.web3

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class Web3Processor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{datasource-web3}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{datasource-web3}"))
        context.onApplyRule("core/datasource-web3", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("web3j"))
    }

    companion object {
        const val ID = "web3"
    }

}