package kotli.template.android.compose.datasource.analytics

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.IFeatureType
import kotli.engine.model.FeatureType
import kotli.template.android.compose.datasource.analytics.firebase.FirebaseAnalyticsProcessor

class AnalyticsProvider : AbstractFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): IFeatureType = FeatureType.DataSource

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseAnalyticsProcessor()
    )

    companion object {
        const val ID = "analytics"
    }
}