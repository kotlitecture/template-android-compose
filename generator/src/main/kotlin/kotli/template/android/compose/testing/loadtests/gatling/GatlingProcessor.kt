package kotli.template.android.compose.testing.loadtests.gatling

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext

class GatlingProcessor : AbstractFeatureProcessor() {

    override val id: String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{loadtests-gatling}")
        }
        context.apply("build.gradle") {
            cleanupLine("{loadtests-gatling}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{loadtests-gatling}")
        }
        context.apply("build.gradle") {
            removeLine("{loadtests-gatling}")
        }
        context.apply("testing/loadtests-gatling") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("gatling")
        }
    }

    companion object {
        const val ID = "gatling"
    }

}