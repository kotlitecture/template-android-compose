package core.data.datasource.clipboard

import core.data.datasource.DataSource
import kotlinx.coroutines.flow.Flow

interface ClipboardSource : DataSource {

    fun getChanges(): Flow<String?>

    fun copy(text: String?, label: String? = null)

}