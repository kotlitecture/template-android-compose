package kotli.template.android.compose.dataflow.messaging

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider

class MessagingProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.messaging"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}