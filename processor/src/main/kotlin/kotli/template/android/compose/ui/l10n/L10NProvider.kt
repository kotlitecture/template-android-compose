package kotli.template.android.compose.ui.l10n

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class L10NProvider : BaseFeatureProvider() {

    override fun getId(): String = "ui.l10n"
    override fun getType(): FeatureType = FeatureTypes.UI
    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}