package kotli.template.android.compose.dataflow.http

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.http.okhttp.OkHttpProcessor
import kotli.template.android.compose.showcases.datasource.http.HttpShowcasesProcessor

class HttpProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.http"

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        HttpShowcasesProcessor::class.java
    )

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        OkHttpProcessor()
    )
}