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
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{userflow.review.market}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.review.market}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("GooglePlayReview"))
        state.onApplyRules("app/src/main/kotlin/app/userflow/review/googleplay", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("googleAppReview")))
    }

    companion object {
        const val ID = "userflow.review.market"
    }

}