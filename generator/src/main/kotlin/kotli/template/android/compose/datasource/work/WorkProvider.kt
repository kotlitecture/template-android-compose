package kotli.template.android.compose.datasource.work

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class WorkProvider : AbstractFeatureProvider() {

    override val id: String = ID
    override val type: FeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        WorkProcessor()
    )

    companion object {
        const val ID = "work"
    }
}