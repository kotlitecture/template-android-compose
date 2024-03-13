package app.datasource.config

import app.datasource.config.firebase.FirebaseRemoteConfigSource
import core.dataflow.datasource.config.DelegateConfigSource
import javax.inject.Inject

class AppConfigSource @Inject constructor() : DelegateConfigSource(
    FirebaseRemoteConfigSource() // {datasource.firebase-config}
) {

    suspend fun getApiTimeout(): Long = getLong("api_timeout") { 30_000 }

    suspend fun getApiRetryCount(): Int = getInt("api_retry_count") { 3 }

}