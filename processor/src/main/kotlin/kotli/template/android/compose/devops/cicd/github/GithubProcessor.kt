package kotli.template.android.compose.devops.cicd.github

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.template.android.compose.devops.distribution.firebase.FirebaseDistributionProcessor
import kotlin.time.Duration.Companion.minutes

object GithubProcessor : BaseFeatureProcessor() {

    const val ID = "devops.cicd.github"

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://github.com/features/actions"
    override fun getIntegrationUrl(state: TemplateState): String = "https://docs.github.com/en/actions/quickstart"

    override fun getIntegrationEstimate(state: TemplateState): Long = 30.minutes.inWholeMilliseconds

    override fun getConfiguration(state: TemplateState): String? {
        if (state.getFeature(FirebaseDistributionProcessor.ID) == null) {
            return null
        }
        return super.getConfiguration(state)
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(".github/workflows", RemoveFile())
    }

}