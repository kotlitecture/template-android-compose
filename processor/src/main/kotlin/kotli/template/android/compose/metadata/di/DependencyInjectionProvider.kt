package kotli.template.android.compose.metadata.di

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.metadata.MetadataProvider
import kotli.template.android.compose.metadata.di.hilt.HiltProcessor

class DependencyInjectionProvider : MetadataProvider() {

    override fun getId(): String = "metadata.di"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        HiltProcessor()
    )

}