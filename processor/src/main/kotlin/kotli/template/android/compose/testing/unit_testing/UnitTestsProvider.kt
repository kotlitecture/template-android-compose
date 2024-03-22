package kotli.template.android.compose.testing.unit_testing

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.testing.unit_testing.robolectric.RobolectricTestingProcessor

class UnitTestsProvider : BaseFeatureProvider() {

    override fun getId(): String = "testing.unit_testing"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.Testing
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        RobolectricTestingProcessor()
    )

}