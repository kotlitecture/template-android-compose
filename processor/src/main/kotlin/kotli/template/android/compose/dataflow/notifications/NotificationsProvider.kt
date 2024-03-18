package kotli.template.android.compose.dataflow.notifications

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.notifications.basic.BasicNotificationsProcessor

class NotificationsProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.notifications"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicNotificationsProcessor()
    )

}