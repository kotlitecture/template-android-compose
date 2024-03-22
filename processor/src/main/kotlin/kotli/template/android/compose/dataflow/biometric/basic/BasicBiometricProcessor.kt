package kotli.template.android.compose.dataflow.biometric.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class BasicBiometricProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        state.onApplyRules("core/data/build.gradle", CleanupMarkedLine("{dataflow.biometric.basic}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data/build.gradle", RemoveMarkedLine("{dataflow.biometric.basic}"))
        state.onApplyRules(VersionCatalogRules(RemoveMarkedLine("androidxBiometric")))
        state.onApplyRules("*BiometricSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.biometric.basic"
    }

}