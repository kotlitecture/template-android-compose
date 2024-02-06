package kotli.template.android.compose.workflow.pincode

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class PincodeProvider : AbstractFeatureProvider() {

    override fun getId(): String = "pincode"

    override fun getType(): IFeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}