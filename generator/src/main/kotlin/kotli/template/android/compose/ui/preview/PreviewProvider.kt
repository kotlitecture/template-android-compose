package kotli.template.android.compose.ui.preview

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class PreviewProvider : AbstractFeatureProvider() {

    override val id: String = "preview"
    override val multiple: Boolean = true
    override val type: FeatureType = FeatureType.UI

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}