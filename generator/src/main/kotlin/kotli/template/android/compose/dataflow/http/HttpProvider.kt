package kotli.template.android.compose.dataflow.http

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class HttpProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.DataFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        HttpProcessor()
    )

    companion object {
        const val ID = "http"
    }
}