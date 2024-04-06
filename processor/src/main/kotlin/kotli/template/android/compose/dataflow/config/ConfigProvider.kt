package kotli.template.android.compose.dataflow.config

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.config.facade.FacadeConfigProcessor
import kotli.template.android.compose.dataflow.config.firebase.FirebaseConfigProcessor

class ConfigProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.config"
    override fun isMultiple(): Boolean = false
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        FacadeConfigProcessor(),
        FirebaseConfigProcessor()
    )
}