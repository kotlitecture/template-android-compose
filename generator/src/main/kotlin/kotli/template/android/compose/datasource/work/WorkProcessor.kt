package kotli.template.android.compose.datasource.work

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class WorkProcessor : FeatureProcessor() {

    override val id: String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{datasource-work}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{datasource-work}")
        }
        context.apply("core/datasource-work") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("androidxWork")
        }
    }

    companion object {
        const val ID = "work"
    }

}