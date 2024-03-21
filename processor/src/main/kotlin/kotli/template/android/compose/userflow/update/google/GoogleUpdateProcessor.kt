package kotli.template.android.compose.userflow.update.google

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GoogleUpdateProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/guide/playcore/in-app-updates"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/guide/playcore/in-app-updates/kotlin-java"

    override fun doApply(state: TemplateState) {
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("userflow.update.google"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{userflow.update.google}"))
        state.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("GoogleUpdateProvider"))
        state.onApplyRules("app/src/main/kotlin/app/userflow/update/google", RemoveFile())
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("googleAppUpdate")))
    }

    companion object {
        const val ID = "userflow.update.google"
    }

}