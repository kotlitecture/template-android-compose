package kotli.template.android.compose.quality.performance

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.performance.firebase.FirebasePerformanceProcessor

class PerformanceProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebasePerformanceProcessor()
    )

    companion object {
        const val ID = "performance"
    }
}