package core.datasource.config

open class DelegateConfigSource(private val source: IConfigSource? = null) : IConfigSource {

    override fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T {
        return source?.get(key, type, defaultValue) ?: defaultValue()
    }

}