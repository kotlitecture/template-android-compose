package kotli.template.android.compose.workflow.onboarding

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType

class OnboardingProvider : AbstractFeatureProvider() {

    override val id: String = "onboarding"

    override val type: FeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}