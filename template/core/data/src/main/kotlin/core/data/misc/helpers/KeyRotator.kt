package core.data.misc.helpers

import java.util.concurrent.atomic.AtomicInteger

class KeyRotator<P>(private val keys: List<P>) {

    private val currentIndex = AtomicInteger(0)

    suspend fun <T> rotate(action: suspend (key: P) -> T): T {
        val index = currentIndex.getAndUpdate { if (it + 1 < keys.size) it + 1 else 0 }
        val permission = keys[index]
        return action(permission)
    }

}