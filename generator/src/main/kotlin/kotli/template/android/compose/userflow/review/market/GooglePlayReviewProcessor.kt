package kotli.template.android.compose.userflow.review.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayReviewProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("settings.gradle",
            CleanupMarkedLine("{market-review}")
        )
        context.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{market-review}")
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle",
            RemoveMarkedLine("{market-review}")
        )
        context.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{market-review}")
        )
        context.onApplyRules("integration/market-review",
            RemoveFile()
        )
        context.onApplyRules("app/src/main/kotlin/di/ProvidesReviewSource.kt",
            RemoveFile()
        )
        context.onApplyVersionCatalogRules(
            RemoveMarkedLine("googleAppReview")
        )
    }

    companion object {
        const val ID = "google-play-review"
    }

}