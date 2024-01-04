package core.datasource.config

import core.datasource.IDataSource

interface IConfigSource : IDataSource {

    fun <T> get(key: String, type: Class<T>, defaultValue: () -> T): T

    fun getString(key: String, defaultValue: () -> String): String {
        return get(key, String::class.java, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: () -> Boolean): Boolean {
        return get(key, Boolean::class.java, defaultValue)
    }

    fun getLong(key: String, defaultValue: () -> Long): Long {
        return get(key, Long::class.java, defaultValue)
    }

    fun getInt(key: String, defaultValue: () -> Int): Int {
        return get(key, Int::class.java, defaultValue)
    }

    fun getDouble(key: String, defaultValue: () -> Double): Double {
        return get(key, Double::class.java, defaultValue)
    }

    fun getFloat(key: String, defaultValue: () -> Float): Float {
        return get(key, Float::class.java, defaultValue)
    }

}