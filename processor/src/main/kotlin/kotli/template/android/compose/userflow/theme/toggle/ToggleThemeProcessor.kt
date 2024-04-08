package kotli.template.android.compose.userflow.theme.toggle

import kotli.engine.BaseFeatureProcessor
import kotli.engine.FeatureProcessor
import kotli.engine.TemplateState
import kotli.engine.template.rule.RemoveFile
import kotli.template.android.compose.showcases.theme.toggle.ToggleThemeShowcasesProcessor
import kotli.template.android.compose.ui.component.basic.BasicComponentsProcessor
import kotlin.time.Duration.Companion.hours

object ToggleThemeProcessor : BaseFeatureProcessor() {

    const val ID = "userflow.theme.toggle"

    override fun getId(): String = ID
    override fun getIntegrationEstimate(state: TemplateState): Long = 1.hours.inWholeMilliseconds

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        ToggleThemeShowcasesProcessor::class.java,
        BasicComponentsProcessor::class.java
    )

    override fun doRemove(state: TemplateState) {
        state.onApplyRules("app/src/main/kotlin/app/userflow/theme/toggle", RemoveFile())
    }

}