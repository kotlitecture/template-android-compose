package kotli.template.android.compose.dataflow.http

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class HttpProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): FeatureType = FeatureTypes.DataFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        HttpProcessor()
    )

    companion object {
        const val ID = "http"
    }
}