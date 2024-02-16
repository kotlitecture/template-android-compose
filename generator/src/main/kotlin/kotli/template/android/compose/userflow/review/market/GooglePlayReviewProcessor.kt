package kotli.template.android.compose.userflow.review.market

import kotli.engine.AbstractFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.applyVersionCatalog

class GooglePlayReviewProcessor : AbstractFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.apply("settings.gradle") {
            cleanupLine("{market-review}")
        }
        context.apply("app/build.gradle") {
            cleanupLine("{market-review}")
        }
    }

    override fun doRemove(context: TemplateContext) {
        context.apply("settings.gradle") {
            removeLine("{market-review}")
        }
        context.apply("app/build.gradle") {
            removeLine("{market-review}")
        }
        context.apply("integration/market-review") {
            remove()
        }
        context.apply("app/src/main/kotlin/di/ProvidesReviewSource.kt") {
            remove()
        }
        context.applyVersionCatalog {
            removeLine("googleAppReview")
        }
    }

    companion object {
        const val ID = "google-play-review"
    }

}