package core.data.datasource.config

open class DelegateConfigSource(private val source: ConfigSource? = null) : ConfigSource {

    override fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T {
        return source?.get(key, type, defaultValue) ?: defaultValue()
    }

}