package kotli.template.android.compose.dataflow.work

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class WorkProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun getType(): IFeatureType = FeatureType.DataFlow

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        WorkProcessor()
    )

    companion object {
        const val ID = "work"
    }
}