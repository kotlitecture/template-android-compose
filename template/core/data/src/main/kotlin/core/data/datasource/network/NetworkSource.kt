package core.data.datasource.network

import core.data.datasource.DataSource
import kotlinx.coroutines.flow.Flow

interface NetworkSource : DataSource {

    suspend fun isOnline(): Flow<Boolean>

    suspend fun getIp(): String?

}