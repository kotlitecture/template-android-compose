package core.data.datasource.storage.keyvalue

import core.data.datasource.DataSource

interface KeyValueSource : DataSource {

    fun <T> save(key: String, value: T, valueType: Class<T>): T

    fun <T> read(key: String, returnType: Class<T>): T?

    fun remove(key: String)

    fun clear()

}