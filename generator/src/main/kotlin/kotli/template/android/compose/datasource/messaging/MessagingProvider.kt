package kotli.template.android.compose.datasource.messaging

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class MessagingProvider : AbstractFeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "messaging"
    }
}