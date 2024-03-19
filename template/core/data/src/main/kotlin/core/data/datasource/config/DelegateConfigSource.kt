package core.data.datasource.config

/**
 *
 * Implementation of the [ConfigSource] interface that delegates calls to another [ConfigSource] instance.
 *
 * If the provided source is null, the default values are returned for all keys.
 */
open class DelegateConfigSource(private val source: ConfigSource? = null) : ConfigSource {

    override fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T {
        return source?.get(key, type, defaultValue) ?: defaultValue()
    }

}