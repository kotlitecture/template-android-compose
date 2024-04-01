package kotli.template.android.compose.dataflow.work

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.work.jetpack.WorkManagerProcessor

class WorkProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.work"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        WorkManagerProcessor()
    )

}