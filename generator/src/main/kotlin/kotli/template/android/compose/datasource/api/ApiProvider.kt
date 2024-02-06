package kotli.template.android.compose.datasource.api

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class ApiProvider : AbstractFeatureProvider() {

    override val id: String = ID
    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "api"
    }
}