package kotli.template.android.compose.appearance.l10n

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class L10NProvider : AbstractFeatureProvider() {

    override val id: String = "l10n_flow"

    override val type: FeatureType = FeatureType.Appearance

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}