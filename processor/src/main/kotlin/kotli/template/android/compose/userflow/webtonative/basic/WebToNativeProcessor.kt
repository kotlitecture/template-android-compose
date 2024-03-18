package kotli.template.android.compose.userflow.webtonative.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class WebToNativeProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{userflow.webtonative.basic}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.webtonative.basic}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("WebToNativeDestination"))
        state.onApplyRules("app/src/main/kotlin/app/userflow/webtonative", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxWebkit")))
    }

    companion object {
        const val ID = "userflow.webtonative.basic"
    }

}