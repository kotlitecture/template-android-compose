package core.datasource.update

import core.datasource.IDataSource
import kotlinx.coroutines.flow.Flow

interface IUpdateSource : IDataSource {

    suspend fun isAvailable(): Flow<Boolean>

    suspend fun startUpdate()

}