package kotli.template.android.compose.ui.theme

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.ui.theme.advanced.AdvancedThemeProcessor
import kotli.template.android.compose.ui.theme.basic.BasicThemeProcessor

object UiThemeProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.theme"
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicThemeProcessor,
        AdvancedThemeProcessor
    )

}