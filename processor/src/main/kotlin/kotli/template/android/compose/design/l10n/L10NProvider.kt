package kotli.template.android.compose.design.l10n

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class L10NProvider : BaseFeatureProvider() {

    override fun getId(): String = "l10n_flow"
    override fun getType(): FeatureType = FeatureTypes.Design

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}