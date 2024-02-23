package kotli.template.android.compose.dataflow.web3

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class Web3Provider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): FeatureType = FeatureTypes.DataFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        Web3Processor()
    )

    companion object {
        const val ID = "web3"
    }
}