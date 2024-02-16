package kotli.template.android.compose.userflow.kyc

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class KycProvider : AbstractFeatureProvider() {

    override fun getId(): String = "kyc"
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}