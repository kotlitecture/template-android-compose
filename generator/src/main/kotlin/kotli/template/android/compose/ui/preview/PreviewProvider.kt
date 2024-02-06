package kotli.template.android.compose.ui.preview

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class PreviewProvider : AbstractFeatureProvider() {

    override fun getId(): String = "preview"
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.UI

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}