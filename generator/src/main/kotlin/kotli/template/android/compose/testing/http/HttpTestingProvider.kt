package kotli.template.android.compose.testing.http

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class HttpTestingProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.Testing

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        HttpTestingProcessor()
    )

    companion object {
        const val ID = "testing-http"
    }
}