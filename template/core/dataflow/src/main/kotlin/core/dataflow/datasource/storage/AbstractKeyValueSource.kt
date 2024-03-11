package core.dataflow.datasource.storage

import android.content.SharedPreferences
import core.dataflow.misc.utils.GsonUtils
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
abstract class AbstractKeyValueSource : KeyValueSource {

    private val cache = ConcurrentHashMap<String, Any?>()

    private val sharedPrefs by lazy { createSharedPreferences() }

    protected abstract fun createSharedPreferences(): SharedPreferences

    protected inline fun <reified T> read(key: String): T? = read(key, T::class.java)

    protected inline fun <reified T> save(key: String, value: T) = save(key, value, T::class.java)

    override fun <T> save(key: String, value: T, valueType: Class<T>): T {
        val editor = sharedPrefs.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Double -> editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            else -> {
                val json = GsonUtils.gson.toJson(value, valueType)
                editor.putString(key, json)
            }
        }
        cache[key] = value
        editor.apply()
        return value
    }

    override fun <T> read(key: String, returnType: Class<T>): T? {
        if (cache.contains(key)) {
            return cache[key] as T?
        }
        if (!sharedPrefs.contains(key)) {
            return null
        }
        val defaultValue = getDumbReturnValue(returnType)
        val value: Any? = when (returnType) {
            String::class.java -> sharedPrefs.getString(key, defaultValue as? String)

            java.lang.Boolean::class.java,
            Boolean::class.java -> sharedPrefs.getBoolean(key, defaultValue as Boolean)

            java.lang.Integer::class.java,
            Int::class.java -> sharedPrefs.getInt(key, defaultValue as Int)

            java.lang.Long::class.java,
            Long::class.java -> sharedPrefs.getLong(key, defaultValue as Long)

            java.lang.Float::class.java,
            Float::class.java -> sharedPrefs.getFloat(key, defaultValue as Float)

            java.lang.Double::class.java,
            Double::class.java -> java.lang.Double.longBitsToDouble(
                sharedPrefs.getLong(
                    key,
                    defaultValue as Long
                )
            )

            else -> {
                val json = sharedPrefs.getString(key, defaultValue as? String)
                GsonUtils.toObject(json, returnType)
            }
        }
        cache[key] = value
        return value as? T
    }

    override fun remove(key: String) {
        cache.remove(key)
        return sharedPrefs.edit().remove(key).apply()
    }

    override fun clear() {
        cache.clear()
        return sharedPrefs
            .edit()
            .clear()
            .apply()
    }

    private fun <T> getDumbReturnValue(returnType: Class<T>): T {
        val defaultValue: Any? = when (returnType) {
            String::class.java -> null
            java.lang.Boolean::class.java,
            Boolean::class.java -> false

            java.lang.Integer::class.java,
            Int::class.java -> 0

            java.lang.Long::class.java,
            Long::class.java -> 0L

            java.lang.Float::class.java,
            Float::class.java -> 0f

            java.lang.Double::class.java,
            Double::class.java -> 0.0

            else -> null
        }
        return defaultValue as T
    }

}