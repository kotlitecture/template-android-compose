package kotli.template.android.compose.quality.performance

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.quality.performance.firebase.FirebasePerformanceProcessor
import kotli.template.android.compose.quality.performance.metrics.MetricsProcessor

class PerformanceProvider : BaseFeatureProvider() {

    override fun getId(): String = "quality.performance"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Quality
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        MetricsProcessor,
        FirebasePerformanceProcessor
    )

}