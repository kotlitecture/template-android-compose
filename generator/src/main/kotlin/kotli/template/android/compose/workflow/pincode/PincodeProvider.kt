package kotli.template.android.compose.workflow.pincode

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class PincodeProvider : FeatureProvider() {

    override val id: String = "pincode"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}