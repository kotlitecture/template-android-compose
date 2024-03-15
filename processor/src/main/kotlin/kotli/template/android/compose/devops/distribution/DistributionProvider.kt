package kotli.template.android.compose.devops.distribution

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.devops.distribution.firebase.FirebaseDistributionProcessor
import kotli.template.android.compose.devops.distribution.googleplay.GooglePlayDistributionProcessor

class DistributionProvider : BaseFeatureProvider() {

    override fun getId(): String = "devops.distribution"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.DevOps
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        FirebaseDistributionProcessor(),
        GooglePlayDistributionProcessor()
    )

}