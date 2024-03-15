package kotli.template.android.compose.dataflow.api

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class ApiProvider : BaseFeatureProvider() {

    override fun getId(): String = "dataflow.api"
    override fun getType(): FeatureType = FeatureTypes.DataFlow
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}