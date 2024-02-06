package kotli.template.android.compose.datasource.storage.objectbox

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext

class ObjectBoxProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
    )

    override fun doApply(context: TemplateContext) {
        context.apply("build.gradle") {
            cleanupLine("{objectbox}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{objectbox}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("build.gradle") {
            removeLine("{objectbox}")
        }
        context.apply("app/build.gradle") {
            removeLine("{objectbox}")
        }
        context.applyVersionCatalog {
            removeLine("objectbox")
        }
    }

    companion object {
        const val ID = "objectbox"
    }

}