package kotli.template.android.compose.transitive.googleservices

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext

class GoogleServicesProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://cloud.google.com/"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/android/setup"

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