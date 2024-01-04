package core.datasource.notifications

import core.datasource.IDataSource

interface INotificationsSource : IDataSource {

    suspend fun enable()

    suspend fun isEnabled(): Boolean

    suspend fun enable(channelId: String)

    suspend fun disable(channelId: String)

    suspend fun isEnabled(channelId: String): Boolean

}