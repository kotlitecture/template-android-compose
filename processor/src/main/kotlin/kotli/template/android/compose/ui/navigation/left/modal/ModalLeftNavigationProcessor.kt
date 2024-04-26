package kotli.template.android.compose.ui.navigation.left.modal

import kotli.engine.BaseFeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.ui.navigation.NavigationBarProviderRules
import kotli.template.android.compose.ui.navigation.adaptive.AdaptiveNavigationProcessor
import kotlin.time.Duration.Companion.hours

object ModalLeftNavigationProcessor : BaseFeatureProcessor() {

    const val ID = "ui.navigation.left.modal"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun doApply(state: TemplateState) {
        if (state.getFeature(AdaptiveNavigationProcessor.ID) == null) {
            state.onApplyRules(
                NavigationBarProviderRules(
                    ReplaceMarkedText(
                        "content()",
                        "content()",
                        "app.ui.navigation.left.ModalLeftNavigation(content)"
                    )
                )
            )
        }
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/left/ModalLeftNavigation.kt",
            RemoveFile()
        )
    }

}