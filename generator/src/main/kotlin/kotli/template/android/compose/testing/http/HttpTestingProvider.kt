package kotli.template.android.compose.testing.http

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class HttpTestingProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Testing

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        HttpTestingProcessor()
    )

    companion object {
        const val ID = "testing-http"
    }
}