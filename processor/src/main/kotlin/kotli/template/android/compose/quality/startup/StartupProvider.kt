package kotli.template.android.compose.quality.startup

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.quality.startup.baselineprofile.BaselineProfileProcessor

class StartupProvider : BaseFeatureProvider() {

    override fun getId(): String = "quality.startup"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Quality
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BaselineProfileProcessor()
    )

}