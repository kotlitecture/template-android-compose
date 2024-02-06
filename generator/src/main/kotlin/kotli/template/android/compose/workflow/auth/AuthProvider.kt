package kotli.template.android.compose.workflow.auth

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class AuthProvider : AbstractFeatureProvider() {

    override val id: String = "auth"
    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}