package kotli.template.android.compose.devops.gradle

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class GradleProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = false
    override fun getType(): FeatureType = FeatureTypes.DevOps

    override fun createProcessors(): List<FeatureProcessor> = emptyList()

    companion object {
        const val ID = "gradle"
    }
}