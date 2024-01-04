package kotli.template.android.compose.quality.performance

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.performance.firebase.FirebasePerformanceProcessor

class PerformanceProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebasePerformanceProcessor()
    )

    companion object {
        const val ID = "performance"
    }
}