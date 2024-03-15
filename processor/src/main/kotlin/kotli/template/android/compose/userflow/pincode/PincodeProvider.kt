package kotli.template.android.compose.userflow.pincode

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class PincodeProvider : BaseFeatureProvider() {

    override fun getId(): String = "userflow.pincode"
    override fun getType(): FeatureType = FeatureTypes.UserFlow
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}