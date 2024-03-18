package kotli.template.android.compose.dataflow.storage

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.storage.encryptedkeyvalue.EncryptedKeyValueProcessor
import kotli.template.android.compose.dataflow.storage.keyvalue.KeyValueProcessor
import kotli.template.android.compose.dataflow.storage.objectbox.ObjectBoxProcessor
import kotli.template.android.compose.dataflow.storage.room.RoomProcessor

class StorageProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.storage"
    override fun isMultiple(): Boolean = true
    override fun createProcessors(): List<FeatureProcessor> = listOf(
        KeyValueProcessor(),
        EncryptedKeyValueProcessor(),
        ObjectBoxProcessor(),
        RoomProcessor()
    )

}