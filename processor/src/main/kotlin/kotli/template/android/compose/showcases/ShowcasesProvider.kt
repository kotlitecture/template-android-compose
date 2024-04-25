package kotli.template.android.compose.showcases

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.showcases.datasource.http.HttpShowcasesProcessor
import kotli.template.android.compose.showcases.datasource.paging.PagingShowcasesProcessor
import kotli.template.android.compose.showcases.userflow.passcode.PasscodeShowcasesProcessor
import kotli.template.android.compose.showcases.userflow.theme.ThemeShowcasesProcessor
import kotli.template.android.compose.showcases.userflow.theme.change.ChangeThemeShowcasesProcessor
import kotli.template.android.compose.showcases.userflow.theme.toggle.ToggleThemeShowcasesProcessor

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
        PasscodeShowcasesProcessor,
        PagingShowcasesProcessor,
        HttpShowcasesProcessor
    )

}