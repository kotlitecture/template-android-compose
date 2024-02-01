package kotli.template.android.compose.build.gradle

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class GradleProvider : AbstractFeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Build

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "gradle"
    }
}