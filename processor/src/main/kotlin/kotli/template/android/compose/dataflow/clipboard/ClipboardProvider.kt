package kotli.template.android.compose.dataflow.clipboard

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.clipboard.basic.BasicClipboardProcessor

class ClipboardProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.clipboard"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicClipboardProcessor()
    )

}