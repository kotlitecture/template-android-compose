package kotli.template.android.compose.transitive

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class TransitiveProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.Transitive

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        GoogleServicesProcessor(),
        FirebaseProcessor()
    )

    companion object {
        const val ID = "transitive"
    }
}