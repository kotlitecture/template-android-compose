package kotli.template.android.compose.userflow.auth

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class AuthProvider : AbstractFeatureProvider() {

    override fun getId(): String = "auth"
    override fun getType(): IFeatureType = FeatureType.UserFlow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}