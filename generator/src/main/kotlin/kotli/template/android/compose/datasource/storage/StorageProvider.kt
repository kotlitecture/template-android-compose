package kotli.template.android.compose.datasource.storage

import kotli.engine.AbstractFeatureProvider
import kotli.engine.IFeatureProcessor
import kotli.engine.model.FeatureType
import kotli.template.android.compose.datasource.storage.objectbox.ObjectBoxProcessor
import kotli.template.android.compose.datasource.storage.room.RoomProcessor

class StorageProvider : AbstractFeatureProvider() {

    override val id: String = ID

    override val type: FeatureType = FeatureType.DataSource

    override fun isMultiple(): Boolean = true

    override fun createProcessors(): List<IFeatureProcessor> = listOf(
        ObjectBoxProcessor(),
        RoomProcessor()
    )

    companion object {
        const val ID = "storage"
    }
}