package kotli.template.android.compose.testing.loadtests

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.testing.loadtests.gatling.GatlingProcessor

class LoadTestsProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = false
    override fun getType(): IFeatureType = FeatureType.Testing

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GatlingProcessor()
    )

    companion object {
        const val ID = "loadtests"
    }
}