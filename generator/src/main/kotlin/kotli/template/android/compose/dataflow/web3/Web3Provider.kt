package kotli.template.android.compose.dataflow.web3

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class Web3Provider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.DataFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        Web3Processor()
    )

    companion object {
        const val ID = "web3"
    }
}