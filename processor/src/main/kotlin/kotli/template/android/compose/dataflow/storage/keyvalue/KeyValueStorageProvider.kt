package kotli.template.android.compose.dataflow.storage.keyvalue

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.storage.keyvalue.datastore.DataStoreProcessor
import kotli.template.android.compose.dataflow.storage.keyvalue.sharedpreferences.SharedPreferencesProcessor

object KeyValueStorageProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.storage.keyvalue"
    override fun isMultiple(): Boolean = false

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        CoreSharedPreferencesKeyValueProcessor,
        CoreKeyValueProcessor,
        AppKeyValueProcessor,
        DataStoreProcessor,
        SharedPreferencesProcessor,
    )

}