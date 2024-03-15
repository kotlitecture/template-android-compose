package kotli.template.android.compose.dataflow.http

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.http.okhttp.OkHttpProcessor

class HttpProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): FeatureType = FeatureTypes.DataFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        OkHttpProcessor()
    )

    companion object {
        const val ID = "http"
    }
}