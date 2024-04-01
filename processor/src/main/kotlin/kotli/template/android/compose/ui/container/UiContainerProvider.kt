package kotli.template.android.compose.ui.container

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor
import kotli.template.android.compose.ui.container.bottomsheet.BottomSheetProcessor
import kotli.template.android.compose.ui.container.motionlayout.MotionLayoutProcessor
import kotli.template.android.compose.ui.container.fixedheaderfooter.FixedHeaderFooterProcessor
import kotli.template.android.compose.ui.container.fixedtopbar.FixedTopBarProcessor

class UiContainerProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.container"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.UI

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicComponentsProcessor::class.java
    )

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        FixedHeaderFooterProcessor(),
        FixedTopBarProcessor(),
        BottomSheetProcessor(),
        MotionLayoutProcessor()
    )

}