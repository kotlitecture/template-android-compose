package kotli.template.android.compose.userflow.webtonative.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.NavigationStateRules
import kotlin.time.Duration.Companion.hours

class WebToNativeProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true
    override fun getIntegrationEstimate(state: TemplateState): Long = 8.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{userflow.webtonative.basic}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(NavigationStateRules(RemoveMarkedLine("WebToNativeDestination")))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.webtonative.basic}"))
        state.onApplyRules("app/src/main/kotlin/app/userflow/webtonative", RemoveFile())
        state.onApplyRules("app/src/main/res/raw/sample.html", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxWebkit")))
    }

    companion object {
        const val ID = "userflow.webtonative.basic"
    }

}