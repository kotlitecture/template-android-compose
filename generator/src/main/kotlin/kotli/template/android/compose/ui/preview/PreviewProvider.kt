package kotli.template.android.compose.ui.preview

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class PreviewProvider : AbstractFeatureProvider() {

    override val id: String = "preview"

    override val type: FeatureType = FeatureType.UI

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}