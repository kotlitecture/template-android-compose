package kotli.template.android.compose.build.vcs

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class VcsProvider : AbstractFeatureProvider() {

    override fun getId(): String = "vcs"
    override fun isMultiple(): Boolean = false
    override fun getType(): IFeatureType = FeatureType.Build

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}