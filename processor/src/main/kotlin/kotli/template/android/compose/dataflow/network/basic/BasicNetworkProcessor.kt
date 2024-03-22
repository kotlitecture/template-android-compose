package kotli.template.android.compose.dataflow.network.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.minutes

class BasicNetworkProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 30.minutes.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*NetworkSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.network.basic"
    }

}