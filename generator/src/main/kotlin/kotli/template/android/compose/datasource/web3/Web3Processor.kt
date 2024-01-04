package kotli.template.android.compose.datasource.web3

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class Web3Processor : FeatureProcessor() {

    override val id: String = ID

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