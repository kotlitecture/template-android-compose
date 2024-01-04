package kotli.template.android.compose.build.vcs

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class VcsProvider : FeatureProvider() {

    override val id: String = "vcs"

    override val type: FeatureType = FeatureType.Build

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}