package kotli.template.android.compose.dataflow.notifications.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotlin.time.Duration.Companion.minutes

class BasicNotificationsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 30.minutes.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*NotificationsSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.notifications.basic"
    }

}