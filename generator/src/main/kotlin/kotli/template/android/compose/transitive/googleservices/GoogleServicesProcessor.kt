package kotli.template.android.compose.transitive.googleservices

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class GoogleServicesProcessor : FeatureProcessor() {

    override val id: String = ID

    override fun getConfiguration(context: TemplateContext): String? {
        return super.getConfiguration(context)?.replace("kotli.app", context.layer.namespace)
    }

    override fun doApply(context: TemplateContext) {
        context.apply("app/build.gradle") {
            cleanupLine("{google-services}")
        }
        context.apply("build.gradle") {
            cleanupLine("{google-services}")
        }
        context.apply("app/google-services.json") {
            replaceText("kotli.app") { context.layer.namespace }
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("app/build.gradle") {
            removeLine("{google-services}")
        }
        context.apply("build.gradle") {
            removeLine("{google-services}")
        }
        context.apply("app/google-services.json") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("google-services")
        }
    }

    companion object {
        const val ID = "google-services"
    }

}