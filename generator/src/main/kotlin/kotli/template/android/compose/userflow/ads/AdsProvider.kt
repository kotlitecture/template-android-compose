package kotli.template.android.compose.userflow.ads

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class AdsProvider : AbstractFeatureProvider() {

    override fun getId(): String = "ads"
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}