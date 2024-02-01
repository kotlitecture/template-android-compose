package kotli.template.android.compose.testing.loadtests

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.testing.loadtests.gatling.GatlingProcessor

class LoadTestsProvider : AbstractFeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Testing

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GatlingProcessor()
    )

    companion object {
        const val ID = "loadtests"
    }
}