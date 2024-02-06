package kotli.template.android.compose.build.distribution

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.build.distribution.firebase.FirebaseDistributionProcessor
import kotli.template.android.compose.build.distribution.googleplay.GooglePlayDistributionProcessor

class DistributionProvider : AbstractFeatureProvider() {

    override val id: String = ID
    override val multiple: Boolean = true
    override val type: FeatureType = FeatureType.Build

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseDistributionProcessor(),
        GooglePlayDistributionProcessor()
    )

    companion object {
        const val ID = "distribution"
    }
}