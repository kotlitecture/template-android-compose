package kotli.template.android.compose.ui.component

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.component.coil.CoilImageProcessor
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor

class UiComponentProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.component"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicComponentsProcessor(),
        CoilImageProcessor()
    )

}