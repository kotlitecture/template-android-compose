package kotli.template.android.compose.workflow.pincode

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class PincodeProvider : AbstractFeatureProvider() {

    override val id: String = "pincode"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}