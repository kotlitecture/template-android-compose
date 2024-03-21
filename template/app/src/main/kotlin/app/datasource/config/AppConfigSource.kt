package app.datasource.config

import app.datasource.config.firebase.FirebaseRemoteConfigSource
import core.data.datasource.config.DelegateConfigSource

class AppConfigSource : DelegateConfigSource(
    FirebaseRemoteConfigSource()
) {
    fun getHttpTimeout(): Long = getLong("http_timeout") { 30_000 }
    fun getHttpRetryCount(): Int = getInt("http_retry_count") { 3 }
    fun getUiLoadingDelay(): Long = getLong("ui_loading_delay") { 50 }
    fun getUiLoadingTimeout(): Long = getLong("ui_loading_timeout") { 30_000 }
}