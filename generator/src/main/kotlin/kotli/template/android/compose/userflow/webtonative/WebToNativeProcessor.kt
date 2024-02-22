package kotli.template.android.compose.userflow.webtonative

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class WebToNativeProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{workflow-webtonative}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{workflow-webtonative}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", CleanupMarkedLine("{workflow-webtonative}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{workflow-webtonative}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{workflow-webtonative}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("{workflow-webtonative}"))
        state.onApplyRules("app/src/main/kotlin/app/feature/webtonative", RemoveFile())
        state.onApplyRules("workflow/webtonative", RemoveFile())
        state.onApplyVersionCatalogRules(RemoveMarkedLine("androidxWebkit"))
    }

    companion object {
        const val ID = "webtonative"
    }

}