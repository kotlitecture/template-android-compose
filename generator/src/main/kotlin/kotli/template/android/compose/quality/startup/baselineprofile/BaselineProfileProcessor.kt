package kotli.template.android.compose.quality.startup.baselineprofile

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateContext
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
        context.onApplyRules("settings.gradle",
            CleanupMarkedLine("{baselineprofile}")
        )
        context.onApplyRules("build.gradle",
            CleanupMarkedLine("{baselineprofile}")
        )
        context.onApplyRules("app/build.gradle",
            CleanupMarkedLine("{baselineprofile}"),
            CleanupMarkedBlock("{baselineprofile-config}")
        )
        context.onApplyRules("baselineprofile/src/main/java/app/baselineprofile/BaselineProfileGenerator.kt",
            ReplaceText("kotli.app") { context.layer.namespace }
        )
    }

    override fun doRemove(context: TemplateContext) {
        context.onApplyRules("settings.gradle",
            RemoveMarkedLine("{baselineprofile}")
        )
        context.onApplyRules("build.gradle",
            RemoveMarkedLine("{baselineprofile}")
        )
        context.onApplyRules("app/build.gradle",
            RemoveMarkedLine("{baselineprofile}"),
            RemoveMarkedBlock("{baselineprofile-config}")
        )
        context.onApplyRules("baselineprofile",
            RemoveFile()
        )
        context.onApplyVersionCatalogRules(
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