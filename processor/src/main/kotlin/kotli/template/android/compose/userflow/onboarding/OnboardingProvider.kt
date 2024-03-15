package kotli.template.android.compose.userflow.onboarding

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes

class OnboardingProvider : BaseFeatureProvider() {

    override fun getId(): String = "userflow.onboarding"
    override fun getType(): FeatureType = FeatureTypes.UserFlow
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}