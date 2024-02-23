package kotli.template.android.compose.dataflow.storage

import kotli.engine.BaseFeatureProvider
import kotli.engine.FeatureProcessor
import kotli.engine.FeatureType
import kotli.engine.model.FeatureTypes
import kotli.template.android.compose.dataflow.storage.objectbox.ObjectBoxProcessor
import kotli.template.android.compose.dataflow.storage.room.RoomProcessor

class StorageProvider : BaseFeatureProvider() {

    override fun getId(): String = ID
    override fun isMultiple(): Boolean = true
    override fun getType(): FeatureType = FeatureTypes.DataFlow

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        ObjectBoxProcessor(),
        RoomProcessor()
    )

    companion object {
        const val ID = "storage"
    }
}