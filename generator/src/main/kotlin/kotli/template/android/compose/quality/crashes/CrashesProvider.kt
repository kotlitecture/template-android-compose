package kotli.template.android.compose.quality.crashes

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.crashes.firebase.FirebaseCrashlyticsProcessor

class CrashesProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = false
    override fun getType(): IFeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseCrashlyticsProcessor()
    )

    companion object {
        const val ID = "crashes"
    }
}