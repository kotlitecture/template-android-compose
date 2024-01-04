package core.datasource.analytics

import core.datasource.IDataSource

interface IAnalyticsSource : IDataSource {

    fun setUserId(id: String?)

    fun setUserProperty(key: String, value: String?)

    fun onError(event: String, error: Throwable)

    fun onEvent(event: String, params: Map<String, String> = mapOf())

    fun onScreenView(screenName: String, params: Map<String, String> = mapOf())

}