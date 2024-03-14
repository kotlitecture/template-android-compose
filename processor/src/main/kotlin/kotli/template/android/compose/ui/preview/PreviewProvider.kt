package kotli.template.android.compose.ui.preview

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.preview.coil.CoilImageProcessor

class PreviewProvider : BaseFeatureProvider() {

    override fun getId(): String = "preview"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.UI

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        CoilImageProcessor()
    )
}