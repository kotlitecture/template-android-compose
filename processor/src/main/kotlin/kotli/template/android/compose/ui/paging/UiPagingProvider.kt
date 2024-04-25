package kotli.template.android.compose.ui.paging

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.ui.paging.jetpack.JetpackComposePagingProcessor

object UiPagingProvider : BaseDataFlowProvider() {

    override fun getId(): String = "ui.paging"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        JetpackComposePagingProcessor
    )

}