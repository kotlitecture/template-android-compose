package kotli.template.android.compose.dataflow.notifications.basic

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile

class BasicNotificationsProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("*NotificationsSource*", RemoveFile())
    }

    companion object {
        const val ID = "dataflow.notifications.basic"
    }

}