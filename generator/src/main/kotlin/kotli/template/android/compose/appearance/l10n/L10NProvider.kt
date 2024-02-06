package kotli.template.android.compose.appearance.l10n

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class L10NProvider : AbstractFeatureProvider() {

    override fun getId(): String = "l10n_flow"
    override fun getType(): IFeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}