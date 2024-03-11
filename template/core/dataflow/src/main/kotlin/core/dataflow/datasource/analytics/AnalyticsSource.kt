package core.dataflow.datasource.analytics

import core.dataflow.datasource.DataSource

interface AnalyticsSource : DataSource {

    fun setUserId(id: String?)

    fun setUserProperty(key: String, value: String?)

    fun onError(event: String, error: Throwable)

    fun onEvent(event: String, params: Map<String, String> = mapOf())

    fun onScreenView(screenName: String, params: Map<String, String> = mapOf())

}