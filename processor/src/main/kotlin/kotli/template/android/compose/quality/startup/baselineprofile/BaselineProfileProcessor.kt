package kotli.template.android.compose.quality.startup.baselineprofile

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedBlock
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedBlock
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceText
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class BaselineProfileProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getWebUrl(state: TemplateState): String = "https://developer.android.com/topic/performance/baselineprofiles/overview"
    override fun getIntegrationUrl(state: TemplateState): String = "https://developer.android.com/topic/performance/baselineprofiles/configure-baselineprofiles"
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds
    override fun getConfigurationEstimate(state: TemplateState): Long = 20.minutes.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "settings.gradle",
            CleanupMarkedLine("{quality.startup.baselineprofile}")
        )
        state.onApplyRules(
            "build.gradle",
            CleanupMarkedLine("{quality.startup.baselineprofile}")
        )
        state.onApplyRules(
            "app/build.gradle",
            CleanupMarkedBlock("{quality.startup.baselineprofile.config}"),
            CleanupMarkedLine("{quality.startup.baselineprofile}"),
        )
        state.onApplyRules(
            "baselineprofile/src/main/java/app/baselineprofile/BaselineProfileGenerator.kt",
            ReplaceText("kotli.app", state.layer.namespace)
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "settings.gradle",
            RemoveMarkedLine("{quality.startup.baselineprofile}")
        )
        state.onApplyRules(
            "build.gradle",
            RemoveMarkedLine("{quality.startup.baselineprofile}")
        )
        state.onApplyRules(
            "app/build.gradle",
            RemoveMarkedBlock("{quality.startup.baselineprofile.config}"),
            RemoveMarkedLine("{quality.startup.baselineprofile}"),
        )
        state.onApplyRules(
            "baselineprofile",
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(
                RemoveMarkedLine("androidxBenchmarkMacroJunit4"),
                RemoveMarkedLine("androidxUiautomator"),
                RemoveMarkedLine("androidxEspresso"),
                RemoveMarkedLine("baselineprofile")
            )
        )
    }

    companion object {
        const val ID = "quality.startup.baselineprofile"
    }

}