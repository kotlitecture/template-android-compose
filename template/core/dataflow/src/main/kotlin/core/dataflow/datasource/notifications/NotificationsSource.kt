package core.dataflow.datasource.notifications

import core.dataflow.datasource.DataSource

interface NotificationsSource : DataSource {

    suspend fun enable()

    suspend fun isEnabled(): Boolean

    suspend fun enable(channelId: String)

    suspend fun disable(channelId: String)

    suspend fun isEnabled(channelId: String): Boolean

}