package kotli.template.android.compose.ui.paging

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.paging.jetpack.JetpackComposePagingProcessor

object UiPagingProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.paging"
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        JetpackComposePagingProcessor
    )

}