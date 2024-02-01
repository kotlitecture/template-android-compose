package kotli.template.android.compose.datasource.analytics

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.datasource.analytics.firebase.FirebaseAnalyticsProcessor

class AnalyticsProvider : AbstractFeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.DataSource

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        FirebaseAnalyticsProcessor()
    )

    companion object {
        const val ID = "analytics"
    }
}