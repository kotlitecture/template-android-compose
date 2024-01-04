package kotli.template.android.compose.datasource.api

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class ApiProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "api"
    }
}