package kotli.template.android.compose.testing.loadtests

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.testing.loadtests.gatling.GatlingProcessor

class LoadTestsProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.Testing

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GatlingProcessor()
    )

    companion object {
        const val ID = "loadtests"
    }
}