package kotli.template.android.compose.dataflow.network

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.network.basic.BasicNetworkProcessor

class NetworkProvider : BaseFeatureProvider() {

    override fun getId(): String = "dataflow.network"
    override fun getType(): FeatureType = FeatureTypes.DataFlow
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicNetworkProcessor()
    )

}