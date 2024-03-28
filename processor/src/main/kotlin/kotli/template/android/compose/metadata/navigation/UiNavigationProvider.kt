package kotli.template.android.compose.metadata.navigation

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.metadata.MetadataProvider
import kotli.template.android.compose.metadata.navigation.jetpack.JetpackProcessor

class UiNavigationProvider : MetadataProvider() {

    override fun getId(): String = "metadata.navigation"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        JetpackProcessor()
    )

}