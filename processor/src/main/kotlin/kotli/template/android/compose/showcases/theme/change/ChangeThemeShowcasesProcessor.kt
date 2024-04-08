package kotli.template.android.compose.showcases.theme.change

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.engine.template.rule.RemoveMarkedLine
import kotli.template.android.compose.showcases.theme.ThemeShowcasesProcessor

object ChangeThemeShowcasesProcessor : BaseFeatureProcessor() {

    const val ID = "showcases.theme.change"

    override fun getId(): String = ID
    override fun isInternal(): Boolean = true

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        ThemeShowcasesProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases/theme/change",
            RemoveFile()
        )
        state.onApplyRules(
            "app/src/main/kotlin/app/showcases/Showcases.kt",
            RemoveMarkedLine("ChangeThemeScreenShowcase"),
            RemoveMarkedLine("ChangeThemeDialogShowcase"),
        )
    }

}