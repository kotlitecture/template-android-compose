package kotli.template.android.compose.essentials.di

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.essentials.EssentialsProvider
import kotli.template.android.compose.essentials.di.hilt.HiltProcessor

class DependencyInjectionProvider : EssentialsProvider() {

    override fun getId(): String = "metadata.di"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        HiltProcessor()
    )

}