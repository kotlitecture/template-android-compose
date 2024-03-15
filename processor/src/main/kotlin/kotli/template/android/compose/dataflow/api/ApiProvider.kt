package kotli.template.android.compose.dataflow.api

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider

class ApiProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.api"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}