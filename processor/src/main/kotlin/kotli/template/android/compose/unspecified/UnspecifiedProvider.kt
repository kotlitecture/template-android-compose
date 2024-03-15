package kotli.template.android.compose.unspecified

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.unspecified.firebase.FirebaseProcessor
import kotli.template.android.compose.unspecified.googleservices.GoogleServicesProcessor
import kotli.template.android.compose.unspecified.startup.StartupInitializerProcessor

class UnspecifiedProvider : BaseFeatureProvider() {

    override fun getId(): String = "unspecified"
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Unspecified
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GoogleServicesProcessor(),
        FirebaseProcessor(),
        StartupInitializerProcessor()
    )

}