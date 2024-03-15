package app.datasource.config

import app.datasource.config.firebase.FirebaseRemoteConfigSource
import core.data.datasource.config.DelegateConfigSource
import javax.inject.Inject

class AppConfigSource @Inject constructor() : DelegateConfigSource(
    FirebaseRemoteConfigSource()
) {

    fun getApiTimeout(): Long = getLong("api_timeout") { 30_000 }
    fun getApiRetryCount(): Int = getInt("api_retry_count") { 3 }

    fun getUiLoadingDelay():Long = getLong("ui_loading_delay") { 50 }
    fun getUiLoadingTimeout():Long = getLong("ui_loading_timeout") { 30_000 }

}