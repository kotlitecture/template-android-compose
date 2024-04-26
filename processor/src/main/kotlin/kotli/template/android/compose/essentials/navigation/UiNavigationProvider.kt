package kotli.template.android.compose.essentials.navigation

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.essentials.EssentialsProvider
import kotli.template.android.compose.essentials.navigation.jetpack.JetpackProcessor

class UiNavigationProvider : EssentialsProvider() {

    override fun getId(): String = "metadata.navigation"
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        JetpackProcessor()
    )

}