package kotli.template.android.compose.quality.performance.firebase

import kotli.engine.FeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebasePerformanceProcessor : FeatureProcessor() {

    override val id: String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://firebase.google.com/docs/perf-mon"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://firebase.google.com/docs/perf-mon/get-started-android"

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{firebase-perf}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{firebase-perf}")
        }
        context.apply("build.gradle") {
            cleanupLine("{firebase-perf}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{firebase-perf}")
        }
        context.apply("app/build.gradle") {
            removeLine("{firebase-perf}")
        }
        context.apply("build.gradle") {
            removeLine("{firebase-perf}")
        }
        context.apply("integration/firebase-perf") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("firebase-perf")
        }
    }

    companion object {
        const val ID = "firebase-performance"
    }

}