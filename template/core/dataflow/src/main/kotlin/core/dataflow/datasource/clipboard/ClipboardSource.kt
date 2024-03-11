package core.dataflow.datasource.clipboard

import core.dataflow.datasource.DataSource
import kotlinx.coroutines.flow.Flow

interface ClipboardSource : DataSource {

    fun getChanges(): Flow<String?>

    fun copy(text: String?, label: String? = null)

}