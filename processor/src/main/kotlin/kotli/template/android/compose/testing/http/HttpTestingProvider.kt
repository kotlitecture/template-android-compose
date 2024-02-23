package kotli.template.android.compose.testing.http

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class HttpTestingProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Testing

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        HttpTestingProcessor()
    )

    companion object {
        const val ID = "testing-http"
    }
}