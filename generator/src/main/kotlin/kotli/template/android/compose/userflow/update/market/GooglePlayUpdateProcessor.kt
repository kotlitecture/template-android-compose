package kotli.template.android.compose.userflow.update.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayUpdateProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("settings.gradle", CleanupMarkedLine("{market-update}"))
        context.onApplyRules("app/build.gradle", CleanupMarkedLine("{market-update}"))
        context.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", CleanupMarkedLine("{market-update}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle", RemoveMarkedLine("{market-update}"))
        context.onApplyRules("app/build.gradle", RemoveMarkedLine("{market-update}"))
        context.onApplyRules("integration/market-update", RemoveFile())
        context.onApplyRules("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("{market-update}"))
        context.onApplyRules("app/src/main/kotlin/di/ProvidesUpdateSource.kt", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("googleAppUpdate"))
    }

    companion object {
        const val ID = "google-play-update"
    }

}