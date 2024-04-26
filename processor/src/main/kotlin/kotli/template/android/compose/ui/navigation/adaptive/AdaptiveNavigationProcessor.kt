package kotli.template.android.compose.ui.navigation.adaptive

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceMarkedText
import kotli.template.android.compose.AppActivityRules
import kotli.template.android.compose.ui.navigation.NavigationBarProcessor
import kotli.template.android.compose.ui.navigation.NavigationBarProviderRules
import kotli.template.android.compose.ui.navigation.bottom.BottomNavigationProcessor
import kotli.template.android.compose.ui.navigation.left.dismissible.DismissibleLeftNavigationProcessor
import kotli.template.android.compose.ui.navigation.left.modal.ModalLeftNavigationProcessor
import kotli.template.android.compose.ui.navigation.left.permanent.PermanentLeftNavigationProcessor
import kotli.template.android.compose.ui.navigation.left.rail.RailNavigationProcessor
import kotlin.time.Duration.Companion.hours

object AdaptiveNavigationProcessor : BaseFeatureProcessor() {

    const val ID = "ui.navigation.adaptive"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        NavigationBarProcessor::class.java,
        BottomNavigationProcessor::class.java,
        ModalLeftNavigationProcessor::class.java,
        DismissibleLeftNavigationProcessor::class.java,
        PermanentLeftNavigationProcessor::class.java,
        RailNavigationProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            NavigationBarProviderRules(
                ReplaceMarkedText(
                    "content()",
                    "content()",
                    "app.ui.navigation.adaptive.AdaptiveNavigation(content)"
                )
            )
        )
        state.onApplyRules(
            AppActivityRules(
                RemoveMarkedLine("BottomNavigation")
            )
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/navigation/adaptive",
            RemoveFile()
        )
    }

}