package kotli.template.android.compose.devops.vcs

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class VcsProvider : BaseFeatureProvider() {

    override fun getId(): String = "vcs"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.DevOps

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}