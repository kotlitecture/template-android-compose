package kotli.template.android.compose.dataflow.config

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.dataflow.config.firebase.FirebaseConfigProcessor

class ConfigProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.DataFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseConfigProcessor()
    )

    companion object {
        const val ID = "config"
    }
}