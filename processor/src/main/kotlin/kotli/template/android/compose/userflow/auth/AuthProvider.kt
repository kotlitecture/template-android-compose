package kotli.template.android.compose.userflow.auth

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class AuthProvider : BaseFeatureProvider() {

    override fun getId(): String = "userflow.auth"
    override fun getType(): FeatureType = FeatureTypes.UserFlow
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}