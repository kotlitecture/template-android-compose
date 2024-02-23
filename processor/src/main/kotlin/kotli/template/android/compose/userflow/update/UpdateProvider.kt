package kotli.template.android.compose.userflow.update

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.userflow.update.market.GooglePlayUpdateProcessor

class UpdateProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): FeatureType = FeatureTypes.UserFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GooglePlayUpdateProcessor()
    )

    companion object {
        const val ID = "update"
    }
}