package kotli.template.android.compose.dataflow.analytics

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.analytics.firebase.FirebaseAnalyticsProcessor

class AnalyticsProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.DataFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        AnalyticsProcessor(),
        FirebaseAnalyticsProcessor()
    )

    companion object {
        const val ID = "analytics"
    }
}