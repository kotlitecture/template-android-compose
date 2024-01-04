package core.datasource.storage

import core.datasource.IDataSource

interface IKeyValueSource : IDataSource {

    fun <T> save(key: String, value: T, valueType: Class<T>): T

    fun <T> read(key: String, returnType: Class<T>): T?

    fun remove(key: String)

    fun clear()

}