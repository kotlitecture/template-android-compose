package kotli.template.android.compose.quality.crashes

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.crashes.firebase.FirebaseCrashlyticsProcessor

class CrashesProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseCrashlyticsProcessor()
    )

    companion object {
        const val ID = "crashes"
    }
}