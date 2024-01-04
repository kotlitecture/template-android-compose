package kotli.template.android.compose.build.distribution

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.build.distribution.firebase.FirebaseDistributionProcessor
import kotli.template.android.compose.build.distribution.googleplay.GooglePlayDistributionProcessor

class DistributionProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Build

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseDistributionProcessor(),
        GooglePlayDistributionProcessor()
    )

    companion object {
        const val ID = "distribution"
    }
}