package kotli.template.android.compose.metadata.design

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.metadata.MetadataProvider
import kotli.template.android.compose.metadata.design.material3.Material3Processor

class UiDesignSystemProvider : MetadataProvider() {

    override fun getId(): String = "metadata.design"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        Material3Processor()
    )

}