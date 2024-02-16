package kotli.template.android.compose.userflow.update

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.userflow.update.market.GooglePlayUpdateProcessor

class UpdateProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GooglePlayUpdateProcessor()
    )

    companion object {
        const val ID = "update"
    }
}