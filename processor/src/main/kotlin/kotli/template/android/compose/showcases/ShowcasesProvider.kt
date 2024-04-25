package kotli.template.android.compose.showcases

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.showcases.useflow.passcode.PasscodeShowcasesProcessor
import kotli.template.android.compose.showcases.useflow.theme.ThemeShowcasesProcessor
import kotli.template.android.compose.showcases.useflow.theme.change.ChangeThemeShowcasesProcessor
import kotli.template.android.compose.showcases.useflow.theme.toggle.ToggleThemeShowcasesProcessor

object ShowcasesProvider : BaseFeatureProvider() {

    override fun getId(): String = "showcases"
    override fun getType(): FeatureType = FeatureTypes.Examples

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        ShowcasesProcessor::class.java
    )

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ShowcasesProcessor,
        ThemeShowcasesProcessor,
        ChangeThemeShowcasesProcessor,
        ToggleThemeShowcasesProcessor,
        PasscodeShowcasesProcessor
    )

}