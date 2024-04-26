package kotli.template.android.compose.essentials.toolkit

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.essentials.EssentialsProvider
import kotli.template.android.compose.essentials.toolkit.compose.ComposeProcessor

class UiToolkitProvider : EssentialsProvider() {

    override fun getId(): String = "metadata.toolkit"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ComposeProcessor()
    )

}