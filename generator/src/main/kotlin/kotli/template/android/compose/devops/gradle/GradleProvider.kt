package kotli.template.android.compose.devops.gradle

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class GradleProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = false
    override fun getType(): IFeatureType = FeatureType.DevOps

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()

    companion object {
        const val ID = "gradle"
    }
}