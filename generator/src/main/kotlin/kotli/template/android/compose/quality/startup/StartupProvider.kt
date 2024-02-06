package kotli.template.android.compose.quality.startup

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.quality.startup.baselineprofile.BaselineProfileProcessor

class StartupProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.Quality

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        BaselineProfileProcessor()
    )

    companion object {
        const val ID = "startup"
    }
}