package kotli.template.android.compose.testing.http

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.testing.http.okhttp.OkHttpTestingProcessor

class HttpTestingProvider : BaseFeatureProvider() {

    override fun getId(): String = "testing.http"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Testing
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        OkHttpTestingProcessor()
    )

}