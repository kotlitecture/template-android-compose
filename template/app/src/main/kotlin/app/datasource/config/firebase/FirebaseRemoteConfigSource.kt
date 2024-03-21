@file:Suppress("UNCHECKED_CAST")

package app.datasource.config.firebase

import app.BuildConfig
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import core.data.datasource.config.ConfigSource
import core.data.misc.extensions.ifNotEmpty
import core.data.misc.utils.GsonUtils
import java.util.concurrent.TimeUnit

class FirebaseRemoteConfigSource : ConfigSource {

    private val config = lazy {
        Firebase.remoteConfig.apply {
            await(setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = BuildConfig.remoteConfigMinimumFetchIntervalInSeconds
                fetchTimeoutInSeconds = BuildConfig.remoteConfigInitTimeoutInSeconds
            }))
            await(fetch(BuildConfig.remoteConfigMinimumFetchIntervalInSeconds))
            await(activate())
        }
    }

    override fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T {
        val stringValue = config.value.getString(key).ifNotEmpty() ?: return defaultValue()
        val value: Any? = when (type) {
            String::class.java -> stringValue
            Boolean::class.java -> stringValue.toBooleanStrictOrNull()
            Long::class.java -> stringValue.toLongOrNull()
            Int::class.java -> stringValue.toIntOrNull()
            Double::class.java -> stringValue.toDoubleOrNull()
            Float::class.java -> stringValue.toFloatOrNull()
            else -> GsonUtils.toObject(stringValue, type)
        }
        return (value as? T) ?: defaultValue()
    }

    private fun <R> await(task: Task<R>): R? {
        return runCatching {
            Tasks.await(
                task,
                BuildConfig.remoteConfigInitTimeoutInSeconds,
                TimeUnit.SECONDS
            )
        }.getOrNull()
    }

}