package kotli.template.android.compose.userflow.kyc

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class KycProvider : BaseFeatureProvider() {

    override fun getId(): String = "userflow.kyc"
    override fun getType(): FeatureType = FeatureTypes.UserFlow
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}