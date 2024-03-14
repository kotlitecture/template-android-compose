package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

interface ArgsStrategy<D> {

    @Composable
    fun toObject(from: String): D?

    fun toString(from: D): String?

    class JsonString<D> : ArgsStrategy<D> {
        @Composable
        override fun toObject(from: String): D {
            val data: Args<D> = Json.decodeFromString(from)
            return data.data
        }

        override fun toString(from: D): String {
            val data = Args(from)
            return Json.encodeToString(data)
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
        inline fun <reified D> json(): ArgsStrategy<D> = JsonString()

        @Suppress("UNCHECKED_CAST")
        fun <D> memory(): ArgsStrategy<D> = InMemory as ArgsStrategy<D>
    }
}