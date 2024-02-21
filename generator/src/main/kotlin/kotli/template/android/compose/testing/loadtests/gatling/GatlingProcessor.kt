package kotli.template.android.compose.testing.loadtests.gatling

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GatlingProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle", CleanupMarkedLine("{loadtests-gatling}"))
        context.onApplyRule("build.gradle", CleanupMarkedLine("{loadtests-gatling}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle", RemoveMarkedLine("{loadtests-gatling}"))
        context.onApplyRule("build.gradle", RemoveMarkedLine("{loadtests-gatling}"))
        context.onApplyRule("testing/loadtests-gatling", RemoveFile())
        context.onAddVersionCatalogRules(RemoveMarkedLine("gatling"))
    }

    companion object {
        const val ID = "gatling"
    }

}