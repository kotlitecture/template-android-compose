package kotli.template.android.compose.metadata

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

abstract class MetadataProvider : BaseFeatureProvider() {

    override fun isRequired(): Boolean = true
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.Metadata

}