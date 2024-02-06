package kotli.template.android.compose.datasource.api

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class ApiProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "api"
    }
}