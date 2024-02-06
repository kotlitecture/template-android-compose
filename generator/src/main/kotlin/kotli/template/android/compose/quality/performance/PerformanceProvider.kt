package kotli.template.android.compose.quality.performance

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.performance.firebase.FirebasePerformanceProcessor

class PerformanceProvider : AbstractFeatureProvider() {

    override val id: String = ID
    override val multiple: Boolean = true
    override val type: FeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebasePerformanceProcessor()
    )

    companion object {
        const val ID = "performance"
    }
}