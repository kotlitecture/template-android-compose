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
import core.data.serialization.SerializationStrategy
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

    override fun <T> get(key: String, serializationStrategy: SerializationStrategy<T>, defaultValue: () -> T): T {
        val stringValue = config.value.getString(key).ifNotEmpty() ?: return defaultValue()
        val value: Any? = when (serializationStrategy.getType()) {
            java.lang.String::class.java,
            String::class.java -> stringValue

            java.lang.Boolean::class.java,
            Boolean::class.java -> stringValue.toBooleanStrictOrNull()

            java.lang.Integer::class.java,
            Int::class.java -> stringValue.toIntOrNull()

            java.lang.Long::class.java,
            Long::class.java -> stringValue.toLongOrNull()

            java.lang.Float::class.java,
            Float::class.java -> stringValue.toFloatOrNull()

            java.lang.Double::class.java,
            Double::class.java -> stringValue.toDoubleOrNull()

            else -> serializationStrategy.toObject(stringValue)
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