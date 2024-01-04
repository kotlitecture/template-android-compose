package kotli.template.android.compose.testing.logging

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class LoggingProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Testing

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "logging"
    }
}