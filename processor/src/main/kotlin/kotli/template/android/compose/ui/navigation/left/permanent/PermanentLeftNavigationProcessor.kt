package kotli.template.android.compose.ui.navigation.left.permanent

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.ui.navigation.NavigationBarProviderRules
import kotli.template.android.compose.ui.navigation.adaptive.AdaptiveNavigationProcessor
import kotlin.time.Duration.Companion.hours

object PermanentLeftNavigationProcessor : BaseFeatureProcessor() {

    const val ID = "ui.navigation.left.permanent"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        if (state.getFeature(AdaptiveNavigationProcessor.ID) == null) {
            state.onApplyRules(
                NavigationBarProviderRules(
                    ReplaceMarkedText(
                        "content()",
                        "content()",
                        "app.ui.navigation.left.PermanentLeftNavigation(content)"
                    )
                )
            )
        }
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/left/PermanentLeftNavigation.kt",
            RemoveFile()
        )
    }

}