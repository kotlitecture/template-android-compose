package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.util.UUID

interface ArgsStrategy<D> {

    @Composable
    fun toObject(from: String): D?

    fun toString(from: D): String?

    class JsonString<D>(private val serializer: KSerializer<D>) : ArgsStrategy<D> {

        @Composable
        override fun toObject(from: String): D {
            return Json.decodeFromString(serializer, from)
        }

        override fun toString(from: D): String {
            return Json.encodeToString(serializer, from)
        }

    }

    object InMemory : ArgsStrategy<Any> {
        private val cache = mutableMapOf<String, Any?>()

        @Composable
        override fun toObject(from: String): Any? {
            val data = cache[from]
            DisposableEffect(from) {
                onDispose {
                    cache.remove(from)
                }
            }
            return data
        }

        override fun toString(from: Any): String {
            val id = UUID.randomUUID().toString()
            cache[id] = from
            return id
        }
    }

    companion object {
        fun <D> json(serializer: KSerializer<D>): ArgsStrategy<D> = JsonString(serializer)

        @Suppress("UNCHECKED_CAST")
        fun <D> memory(): ArgsStrategy<D> = InMemory as ArgsStrategy<D>
    }
}