package kotli.template.android.compose.ui.navigation.adaptive

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.VersionCatalogRules
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotlin.time.Duration.Companion.hours

class AdaptiveNavigationProcessor : BaseFeatureProcessor() {

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/adaptive",
            RemoveFile()
        )
        state.onApplyRules(
            VersionCatalogRules(
                listOf(
                    RemoveMarkedLine("adaptiveNavigation"),
                    RemoveMarkedLine("adaptive-navigation"),
                )
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/NavigationBarProvider.kt",
            RemoveMarkedLine("AdaptiveNavigation")
        )
    }

    companion object {
        const val ID = "ui.navigation.adaptive"
    }

}