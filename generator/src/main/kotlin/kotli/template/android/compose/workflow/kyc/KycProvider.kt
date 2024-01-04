package kotli.template.android.compose.workflow.kyc

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class KycProvider : FeatureProvider() {

    override val id: String = "kyc"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}