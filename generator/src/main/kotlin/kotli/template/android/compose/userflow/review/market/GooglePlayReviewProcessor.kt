package kotli.template.android.compose.userflow.review.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayReviewProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle",
            CleanupMarkedLine("{market-review}")
        )
        context.onApplyRule("app/build.gradle",
            CleanupMarkedLine("{market-review}")
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle",
            RemoveMarkedLine("{market-review}")
        )
        context.onApplyRule("app/build.gradle",
            RemoveMarkedLine("{market-review}")
        )
        context.onApplyRule("integration/market-review",
            RemoveFile()
        )
        context.onApplyRule("app/src/main/kotlin/di/ProvidesReviewSource.kt",
            RemoveFile()
        )
        context.onAddVersionCatalogRules(
            RemoveMarkedLine("googleAppReview")
        )
    }

    companion object {
        const val ID = "google-play-review"
    }

}