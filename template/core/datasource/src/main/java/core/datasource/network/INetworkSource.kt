package core.datasource.network

import core.datasource.IDataSource
import kotlinx.coroutines.flow.Flow

interface INetworkSource : IDataSource {

    suspend fun isOnline(): Flow<Boolean>

    suspend fun getIp(): String?

}