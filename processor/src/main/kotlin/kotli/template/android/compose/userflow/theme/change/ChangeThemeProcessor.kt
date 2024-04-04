package kotli.template.android.compose.userflow.theme.change

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.AndroidStringsRules
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor
import kotli.template.android.compose.ui.container.fixedtopbar.FixedTopBarProcessor
import kotlin.time.Duration.Companion.hours

object ChangeThemeProcessor : BaseFeatureProcessor() {

    const val ID = "userflow.theme.change"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        BasicComponentsProcessor::class.java,
        FixedTopBarProcessor::class.java,
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/di/state/ProvidesNavigationState.kt",
            RemoveMarkedLine("ChangeThemeDialogDestination"),
            RemoveMarkedLine("ChangeThemeDestination")
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/userflow/theme/change",
            RemoveFile()
        )
        state.onApplyRules(
            AndroidStringsRules(
                RemoveMarkedLine("theme_change_")
            )
        )
    }

}