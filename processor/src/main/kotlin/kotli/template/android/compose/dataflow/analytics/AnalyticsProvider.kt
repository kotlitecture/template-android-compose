package kotli.template.android.compose.dataflow.analytics

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.analytics.basic.BasicAnalyticsProcessor
import kotli.template.android.compose.dataflow.analytics.firebase.FirebaseAnalyticsProcessor

class AnalyticsProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.analytics"
    override fun isMultiple(): Boolean = true
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        BasicAnalyticsProcessor(),
        FirebaseAnalyticsProcessor()
    )
}