package kotli.template.android.compose.userflow.review.google

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GoogleReviewProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{userflow.review.google}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.review.google}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("GoogleReviewProvider"))
        state.onApplyRules("app/src/main/kotlin/app/userflow/review/google", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("googleAppReview")))
    }

    companion object {
        const val ID = "userflow.review.google"
    }

}