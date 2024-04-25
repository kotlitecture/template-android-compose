package kotli.template.android.compose.showcases.useflow.theme.toggle

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.ShowcasesRules
import kotli.template.android.compose.showcases.useflow.theme.ThemeShowcasesProcessor

object ToggleThemeShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.theme.toggle"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        ThemeShowcasesProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases/userflow/theme/toggle",
            RemoveFile()
        )
        state.onApplyRules(
            ShowcasesRules(
                RemoveMarkedLine("ToggleThemeShowcase")
            )
        )
    }

}