package kotli.template.android.compose.quality.startup

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.startup.baselineprofile.BaselineProfileProcessor

class StartupProvider : FeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Quality

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        BaselineProfileProcessor()
    )

    companion object {
        const val ID = "startup"
    }
}