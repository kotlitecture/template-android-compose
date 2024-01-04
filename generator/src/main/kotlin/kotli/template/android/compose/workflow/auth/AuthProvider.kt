package kotli.template.android.compose.workflow.auth

import kotli.engine.FeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class AuthProvider : FeatureProvider() {

    override val id: String = "auth"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}