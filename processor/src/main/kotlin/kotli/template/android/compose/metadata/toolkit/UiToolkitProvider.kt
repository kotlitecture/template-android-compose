package kotli.template.android.compose.metadata.toolkit

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.metadata.MetadataProvider
import kotli.template.android.compose.metadata.toolkit.compose.ComposeProcessor

class UiToolkitProvider : MetadataProvider() {

    override fun getId(): String = "metadata.toolkit"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ComposeProcessor()
    )

}