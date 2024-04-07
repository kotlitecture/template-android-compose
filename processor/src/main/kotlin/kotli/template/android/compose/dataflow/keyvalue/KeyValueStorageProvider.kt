package kotli.template.android.compose.dataflow.keyvalue

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.keyvalue.datastore.DataStoreProcessor
import kotli.template.android.compose.dataflow.keyvalue.sharedpreferences.SharedPreferencesProcessor

object KeyValueStorageProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.keyvalue"
    override fun isMultiple(): Boolean = false

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        CoreSharedPreferencesKeyValueProcessor,
        CoreKeyValueProcessor,
        AppKeyValueProcessor,
        DataStoreProcessor,
        SharedPreferencesProcessor,
    )

}