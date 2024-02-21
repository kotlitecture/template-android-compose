package kotli.template.android.compose.testing.logging

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class LoggingProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.Testing

    override fun createProcessors(): List<FeatureProcessor> = emptyList()

    companion object {
        const val ID = "logging"
    }
}