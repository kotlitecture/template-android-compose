package core.datasource.clipboard

import core.datasource.IDataSource
import kotlinx.coroutines.flow.Flow

interface IClipboardSource : IDataSource {

    fun getChanges(): Flow<String?>

    fun copy(text: String?, label: String? = null)

}