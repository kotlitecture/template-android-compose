package kotli.template.android.compose.dataflow.network

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.network.basic.BasicNetworkProcessor

class NetworkProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.network"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicNetworkProcessor()
    )

}