package kotli.template.android.compose.userflow.update.market

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GooglePlayUpdateProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{market-update}"))
        context.onApplyRule("app/build.gradle", CleanupMarkedLine("{market-update}"))
        context.onApplyRule("app/src/main/kotlin/app/AppActivity.kt", CleanupMarkedLine("{market-update}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{market-update}"))
        context.onApplyRule("app/build.gradle", RemoveMarkedLine("{market-update}"))
        context.onApplyRule("integration/market-update", RemoveFile())
        context.onApplyRule("app/src/main/kotlin/app/AppActivity.kt", RemoveMarkedLine("{market-update}"))
        context.onApplyRule("app/src/main/kotlin/di/ProvidesUpdateSource.kt", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("googleAppUpdate"))
    }

    companion object {
        const val ID = "google-play-update"
    }

}