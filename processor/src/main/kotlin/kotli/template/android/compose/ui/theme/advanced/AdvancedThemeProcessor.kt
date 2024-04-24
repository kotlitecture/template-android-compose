package kotli.template.android.compose.ui.theme.advanced

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.engine.template.rule.ReplaceText
import kotli.template.android.compose.dataflow.keyvalue.sharedpreferences.SharedPreferencesProcessor
import kotlin.time.Duration.Companion.hours

object AdvancedThemeProcessor : BaseFeatureProcessor() {

    const val ID = "ui.theme.advanced"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 2.hours.inWholeMilliseconds
    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        SharedPreferencesProcessor::class.java
    )

    override fun doApply(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/theme/AppThemeViewModel.kt",
            RemoveFile()
        )
    }

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "core/ui/src/main/kotlin/core/ui/theme/ThemeState.kt",
            RemoveMarkedLine(
                marker = "theme_config",
                singleLine = true
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/theme/AppThemeProvider.kt",
            ReplaceText(
                text = "AppThemePersistenceViewModel",
                replacer = "AppThemeViewModel"
            )
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/theme/AppThemeConfigData.kt",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/ui/theme/AppThemePersistenceViewModel.kt",
            RemoveFile()
        )
    }

}