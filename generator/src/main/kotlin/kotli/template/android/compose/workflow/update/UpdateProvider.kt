package kotli.template.android.compose.workflow.update

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.workflow.update.market.GooglePlayUpdateProcessor

class UpdateProvider : AbstractFeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GooglePlayUpdateProcessor()
    )

    companion object {
        const val ID = "update"
    }
}