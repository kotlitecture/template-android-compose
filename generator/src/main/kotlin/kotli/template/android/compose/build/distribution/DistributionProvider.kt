package kotli.template.android.compose.build.distribution

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.build.distribution.firebase.FirebaseDistributionProcessor
import kotli.template.android.compose.build.distribution.googleplay.GooglePlayDistributionProcessor

class DistributionProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID

    override fun isMultiple(): Boolean = true

    override fun getType(): IFeatureType = FeatureType.Build

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseDistributionProcessor(),
        GooglePlayDistributionProcessor()
    )

    companion object {
        const val ID = "distribution"
    }
}