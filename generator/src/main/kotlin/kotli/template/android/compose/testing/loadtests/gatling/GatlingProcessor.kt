package kotli.template.android.compose.testing.loadtests.gatling

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine

class GatlingProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doApply(context: TemplateContext) {
        context.onApplyRules("settings.gradle", CleanupMarkedLine("{loadtests-gatling}"))
        context.onApplyRules("build.gradle", CleanupMarkedLine("{loadtests-gatling}"))
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle", RemoveMarkedLine("{loadtests-gatling}"))
        context.onApplyRules("build.gradle", RemoveMarkedLine("{loadtests-gatling}"))
        context.onApplyRules("testing/loadtests-gatling", RemoveFile())
        context.onApplyVersionCatalogRules(RemoveMarkedLine("gatling"))
    }

    companion object {
        const val ID = "gatling"
    }

}