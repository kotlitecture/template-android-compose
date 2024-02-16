package kotli.template.android.compose.userflow.pincode

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class PincodeProvider : AbstractFeatureProvider() {

    override fun getId(): String = "pincode"

    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}