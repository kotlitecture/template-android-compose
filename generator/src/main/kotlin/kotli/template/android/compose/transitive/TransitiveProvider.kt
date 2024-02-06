package kotli.template.android.compose.transitive

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.transitive.firebase.FirebaseProcessor
import kotli.template.android.compose.transitive.googleservices.GoogleServicesProcessor

class TransitiveProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.Transitive

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        GoogleServicesProcessor(),
        FirebaseProcessor()
    )

    companion object {
        const val ID = "transitive"
    }
}