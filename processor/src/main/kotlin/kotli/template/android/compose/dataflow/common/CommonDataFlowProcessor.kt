package kotli.template.android.compose.dataflow.common

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.CleanupMarkedLine
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.testing.unit_testing.robolectric.RobolectricTestingProcessor

class CommonDataFlowProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        RobolectricTestingProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules("settings.gradle", CleanupMarkedLine("{dataflow.common}"))
        state.onApplyRules("app/build.gradle", CleanupMarkedLine("{dataflow.common}"))
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("core/data", RemoveFile())
        state.onApplyRules("settings.gradle", RemoveMarkedLine("{dataflow.common}"))
        state.onApplyRules("app/build.gradle", RemoveMarkedLine("{dataflow.common}"))
    }

    companion object {
        const val ID = "dataflow.common"
    }

}