package kotli.template.android.compose.datasource.web3

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class Web3Provider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        Web3Processor()
    )

    companion object {
        const val ID = "web3"
    }
}