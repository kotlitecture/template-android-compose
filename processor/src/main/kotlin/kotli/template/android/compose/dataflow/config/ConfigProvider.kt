package kotli.template.android.compose.dataflow.config

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.config.firebase.FirebaseConfigProcessor

class ConfigProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): FeatureType = FeatureTypes.DataFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ConfigProcessor(),
        FirebaseConfigProcessor()
    )

    companion object {
        const val ID = "config"
    }
}