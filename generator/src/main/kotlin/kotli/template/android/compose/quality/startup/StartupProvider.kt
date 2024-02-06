package kotli.template.android.compose.quality.startup

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.startup.baselineprofile.BaselineProfileProcessor

class StartupProvider : AbstractFeatureProvider() {

    override val id: String = ID
    override val multiple: Boolean = true
    override val type: FeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        BaselineProfileProcessor()
    )

    companion object {
        const val ID = "startup"
    }
}