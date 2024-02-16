package kotli.template.android.compose.datasource.web3

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.applyVersionCatalog

class Web3Processor : AbstractFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{datasource-web3}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{datasource-web3}")
        }
        context.apply("core/datasource-web3") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("web3j")
        }
    }

    companion object {
        const val ID = "web3"
    }

}