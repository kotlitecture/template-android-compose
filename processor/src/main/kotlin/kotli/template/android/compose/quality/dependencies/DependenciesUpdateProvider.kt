package kotli.template.android.compose.quality.dependencies

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.quality.dependencies.version_catalog.VersionCatalogProcessor

class DependenciesUpdateProvider : BaseFeatureProvider() {

    override fun getId(): String = "quality.dependencies"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Quality
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        VersionCatalogProcessor()
    )

}