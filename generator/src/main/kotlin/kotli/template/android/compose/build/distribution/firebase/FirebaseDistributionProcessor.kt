package kotli.template.android.compose.build.distribution.firebase

import kotli.engine.FeatureProcessor
import kotli.engine.IFeatureProcessor
import kotli.engine.TemplateContext
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class FirebaseDistributionProcessor : FeatureProcessor() {

    override val id: String = ID

    override fun dependencies(): List<Class<out IFeatureProcessor>> = listOf(
        GoogleServicesProcessor::class.java,
        FirebaseProcessor::class.java
    )

    override fun doApply(context: TemplateContext) {
        context.apply("app/build.gradle") {
            cleanupLine("{firebase-distribution}")
            cleanupBlock("{firebase-distribution-debug}")
            cleanupBlock("{firebase-distribution-staging}")
        }
        context.apply("build.gradle") {
            cleanupLine("{firebase-distribution}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("app/build.gradle") {
            removeLine("{firebase-distribution}")
            removeBlock("{firebase-distribution-debug}")
            removeBlock("{firebase-distribution-staging}")
        }
        context.apply("build.gradle") {
            removeLine("{firebase-distribution}")
        }
        context.applyVersionCatalog {
            removeLine("appdistribution")
        }
    }

    companion object {
        const val ID = "firebase-distribution"
    }

}