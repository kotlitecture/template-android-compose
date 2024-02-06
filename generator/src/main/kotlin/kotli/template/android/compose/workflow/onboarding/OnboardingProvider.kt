package kotli.template.android.compose.workflow.onboarding

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType

class OnboardingProvider : AbstractFeatureProvider() {

    override fun getId(): String = "onboarding"
    override fun getType(): IFeatureType = FeatureType.Workflow

    override fun createProcessors(): List<IFeatureProcessor> = emptyList()
}