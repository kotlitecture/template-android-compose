package kotli.template.android.compose.userflow.auth.google

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class GoogleAuthProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedLine("{userflow.auth.google}")
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedLine("{userflow.auth.google}")
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("googleServicesAuth")
            )
        )
    }

    companion object {
        const val ID = "userflow.auth.google"
    }

}