package kotli.template.android.compose.ui.container

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.container.bottomsheet.BottomSheetProcessor

class UiContainerProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.container"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BottomSheetProcessor()
    )

}