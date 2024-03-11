package core.dataflow.datasource.storage

import core.dataflow.datasource.DataSource

interface KeyValueSource : DataSource {

    fun <T> save(key: String, value: T, valueType: Class<T>): T

    fun <T> read(key: String, returnType: Class<T>): T?

    fun remove(key: String)

    fun clear()

}