@file:Suppress("UNCHECKED_CAST")

package core.datasource.firebase.config

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import core.datasource.analytics.IAnalyticsSource
import core.datasource.config.IConfigSource
import core.datasource.firebase.config.BuildConfig
import core.essentials.misc.extensions.ifNotEmpty
import core.essentials.misc.utils.GsonUtils
import java.util.concurrent.TimeUnit

class FirebaseRemoteConfigSource(private val analytics: IAnalyticsSource) : IConfigSource {

    private val config = lazy { FirebaseRemoteConfig.getInstance() }

    override suspend fun init() {
        val settings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(BuildConfig.remoteConfigMinimumFetchIntervalInSeconds)
            .build()
        await(config.value.setConfigSettingsAsync(settings), "setConfigSettingsAsync")
        await(config.value.fetch(BuildConfig.remoteConfigMinimumFetchIntervalInSeconds), "fetch")
        await(config.value.activate(), "activate")
    }

    override fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T {
        val value: Any? = when (type) {
            String::class.java -> config.value.getString(key).ifNotEmpty()
            Boolean::class.java -> config.value.getBoolean(key)
            Long::class.java -> config.value.getLong(key)
            Int::class.java -> config.value.getLong(key).toInt()
            Double::class.java -> config.value.getDouble(key)
            Float::class.java -> config.value.getDouble(key).toFloat()
            else -> {
                val json = config.value.getString(key)
                GsonUtils.toObject(json, type)
            }
        }
        return (value as? T) ?: defaultValue()
    }

    private fun <R> await(task: Task<R>, taskName: String): R? =
        runCatching {
            Tasks.await(
                task,
                BuildConfig.remoteConfigInitTimeoutInSeconds,
                TimeUnit.SECONDS
            )
        }
            .onFailure { analytics.onError("fetch_remote_config_${taskName}", it) }
            .getOrNull()

}