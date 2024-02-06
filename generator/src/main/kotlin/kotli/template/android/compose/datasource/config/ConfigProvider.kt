package kotli.template.android.compose.datasource.config

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.datasource.config.firebase.FirebaseConfigProcessor

class ConfigProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseConfigProcessor()
    )

    companion object {
        const val ID = "config"
    }
}