package app.datasource.config

import core.datasource.analytics.IAnalyticsSource
import core.datasource.config.DelegateConfigSource
import core.datasource.firebase.config.FirebaseRemoteConfigSource // {firebase-config}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfigSource @Inject constructor(analyticsSource: IAnalyticsSource) : DelegateConfigSource(
    FirebaseRemoteConfigSource(analyticsSource) // {firebase-config}
) {

    fun getApiTimeout(): Long = getLong("api_timeout") { 30_000 }

    fun getApiRetryCount(): Int = getInt("api_retry_count") { 3 }

}