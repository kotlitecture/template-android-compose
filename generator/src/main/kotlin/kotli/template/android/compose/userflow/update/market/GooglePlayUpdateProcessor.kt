package kotli.template.android.compose.userflow.update.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayUpdateProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{market-update}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{market-update}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", CleanupMarkedLine("{market-update}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{market-update}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{market-update}"))
        state.onApplyRules("integration/market-update", RemoveFile())
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("{market-update}"))
        state.onApplyRules("app/src/main/kotlin/di/ProvidesUpdateSource.kt", RemoveFile())
        state.onApplyVersionCatalogRules(RemoveMarkedLine("googleAppUpdate"))
    }

    companion object {
        const val ID = "google-play-update"
    }

}