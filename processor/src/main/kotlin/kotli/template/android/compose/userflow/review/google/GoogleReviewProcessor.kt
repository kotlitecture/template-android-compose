package kotli.template.android.compose.userflow.review.google

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AppActivityRules
import kotlin.time.Duration.Companion.hours

class GoogleReviewProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/guide/playcore/in-app-review"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/guide/playcore/in-app-review/kotlin-java"

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{userflow.review.google}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.review.google}"))
        state.onApplyRules(AppActivityRules(RemoveMarkedLine("GoogleReviewProvider")))
        state.onApplyRules("app/src/main/kotlin/app/userflow/review/google", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("googleAppReview")))
    }

    companion object {
        const val ID = "userflow.review.google"
    }

}