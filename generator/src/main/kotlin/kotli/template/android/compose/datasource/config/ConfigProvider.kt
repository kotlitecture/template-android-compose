package kotli.template.android.compose.datasource.config

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.datasource.config.firebase.FirebaseConfigProcessor

class ConfigProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseConfigProcessor()
    )

    companion object {
        const val ID = "config"
    }
}