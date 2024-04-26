package kotli.template.android.compose.essentials.design

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.essentials.EssentialsProvider
import kotli.template.android.compose.essentials.design.material3.Material3Processor

class UiDesignSystemProvider : EssentialsProvider() {

    override fun getId(): String = "metadata.design"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        Material3Processor
    )

}