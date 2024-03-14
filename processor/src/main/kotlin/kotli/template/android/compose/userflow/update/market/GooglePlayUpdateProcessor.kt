package kotli.template.android.compose.userflow.update.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayUpdateProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("userflow.google-play-update"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.google-play-update}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("GooglePlayUpdate"))
        state.onApplyRules("app/src/main/kotlin/app/userflow/update/googleplay", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("googleAppUpdate")))
    }

    companion object {
        const val ID = "userflow.google-play-update"
    }

}