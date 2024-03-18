package kotli.template.android.compose.dataflow.http

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.http.okhttp.OkHttpProcessor

class HttpProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.http"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        OkHttpProcessor()
    )
}