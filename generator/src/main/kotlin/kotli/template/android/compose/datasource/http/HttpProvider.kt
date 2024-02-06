package kotli.template.android.compose.datasource.http

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class HttpProvider : AbstractFeatureProvider() {

    override val id: String = ID
    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        HttpProcessor()
    )

    companion object {
        const val ID = "http"
    }
}