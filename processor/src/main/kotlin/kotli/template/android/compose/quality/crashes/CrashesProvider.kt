package kotli.template.android.compose.quality.crashes

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.quality.crashes.firebase.FirebaseCrashlyticsProcessor

class CrashesProvider : BaseFeatureProvider() {

    override fun getId(): String = "quality.crashes"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.Quality
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        FirebaseCrashlyticsProcessor()
    )

}