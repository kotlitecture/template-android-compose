package kotli.template.android.compose.userflow.ads

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class AdsProvider : BaseFeatureProvider() {

    override fun getId(): String = "ads"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.UserFlow

    override fun createProcessors(): List<FeatureProcessor> = emptyList()
}