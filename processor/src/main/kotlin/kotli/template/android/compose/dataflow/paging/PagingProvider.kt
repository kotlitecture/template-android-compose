package kotli.template.android.compose.dataflow.paging

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.paging.jetpack.JetpackPagingProcessor

object PagingProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.paging"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        JetpackPagingProcessor
    )

}