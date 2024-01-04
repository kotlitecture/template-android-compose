package kotli.template.android.compose.ui.preview

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class PreviewProvider : FeatureProvider() {

    override val id: String = "preview"

    override val type: FeatureType = FeatureType.UI

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}