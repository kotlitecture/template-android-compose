package kotli.template.android.compose.userflow.onboarding

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.userflow.BaseUserFlowProvider

class OnboardingProvider : BaseUserFlowProvider() {

    override fun getId(): String = "userflow.onboarding"
    override fun createProcessors(): List<FeatureProcessor> = emptyList()

}