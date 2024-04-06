package kotli.template.android.compose.dataflow.storage.database

import kotli.engine.FeatureProcessor
import kotli.template.android.compose.dataflow.BaseDataFlowProvider
import kotli.template.android.compose.dataflow.paging.jetpack.JetpackPagingProcessor
import kotli.template.android.compose.dataflow.storage.database.objectbox.ObjectBoxProcessor
import kotli.template.android.compose.dataflow.storage.database.room.RoomProcessor

object DatabaseProvider : BaseDataFlowProvider() {

    override fun getId(): String = "dataflow.storage.database"
    override fun isMultiple(): Boolean = false

    override fun dependencies(): List<Class<out FeatureProcessor>> = listOf(
        JetpackPagingProcessor::class.java
    )

    override fun createProcessors(): List<FeatureProcessor> = listOf(
        RoomProcessor,
        ObjectBoxProcessor
    )

}