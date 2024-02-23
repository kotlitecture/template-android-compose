package kotli.template.android.compose.userflow.review.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayReviewProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle",
            CleanupMarkedLine("{market-review}")
        )
        state.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{market-review}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("settings.gradle",
            RemoveMarkedLine("{market-review}")
        )
        state.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{market-review}")
        )
        state.onApplyRules("integration/market-review",
            RemoveFile()
        )
        state.onApplyRules("app/src/main/kotlin/di/ProvidesReviewSource.kt",
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(RemoveMarkedLine("googleAppReview"))
        )
    }

    companion object {
        const val ID = "google-play-review"
    }

}