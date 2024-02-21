package kotli.template.android.compose.quality.startup.baselineprofile

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
import kotli.engine.extensions.onAddVersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceText

class BaselineProfileProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(context: TemplateContext): String = "https://developer.android.com/topic/performance/baselineprofiles/overview"
    override fun getIntegrationUrl(context: TemplateContext): String = "https://developer.android.com/topic/performance/baselineprofiles/configure-baselineprofiles"

    override fun doApply(context: TemplateContext) {
        context.onApplyRule("settings.gradle",
            CleanupMarkedLine("{baselineprofile}")
        )
        context.onApplyRule("build.gradle",
            CleanupMarkedLine("{baselineprofile}")
        )
        context.onApplyRule("app/build.gradle",
            CleanupMarkedLine("{baselineprofile}"),
            CleanupMarkedBlock("{baselineprofile-config}")
        )
        context.onApplyRule("baselineprofile/src/main/java/app/baselineprofile/BaselineProfileGenerator.kt",
            ReplaceText("kotli.app") { context.layer.namespace }
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRule("settings.gradle",
            RemoveMarkedLine("{baselineprofile}")
        )
        context.onApplyRule("build.gradle",
            RemoveMarkedLine("{baselineprofile}")
        )
        context.onApplyRule("app/build.gradle",
            RemoveMarkedLine("{baselineprofile}"),
            RemoveMarkedBlock("{baselineprofile-config}")
        )
        context.onApplyRule("baselineprofile",
            RemoveFile()
        )
        context.onAddVersionCatalogRules(
            RemoveMarkedLine("benchmarkMacroJunit4"),
            RemoveMarkedLine("baselineprofile"),
            RemoveMarkedLine("uiautomator"),
            RemoveMarkedLine("espresso")
        )
    }

    companion object {
        const val ID = "baseline-profile"
    }

}