package kotli.template.android.compose.build.distribution.googleplay

import kotli.engine.FeatureProcessor
import kotli.engine.TemplateContext

class GooglePlayDistributionProcessor : FeatureProcessor() {

    override val id: String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://github.com/Triple-T/gradle-play-publisher"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://github.com/Triple-T/gradle-play-publisher?tab=readme-ov-file#quickstart-guide"

    override fun doApply(context: TemplateContext) {
        context.apply("app/build.gradle") {
            cleanupLine("{google-play-distribution}")
            cleanupBlock("{google-play-distribution-config}")
        }
        context.apply("build.gradle") {
            cleanupLine("{google-play-distribution}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("app/build.gradle") {
            removeLine("{google-play-distribution}")
            removeBlock("{google-play-distribution-config}")
        }
        context.apply("build.gradle") {
            removeLine("{google-play-distribution}")
        }
        context.apply("app/assemble/google-play-publisher.json") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("googlePlayPublisher")
        }
    }

    companion object {
        const val ID = "google-play-distribution"
    }

}