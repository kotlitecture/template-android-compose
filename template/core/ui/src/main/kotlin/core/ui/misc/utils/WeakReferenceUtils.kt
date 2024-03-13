@file:Suppress("UNCHECKED_CAST")

package core.ui.misc.utils

import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap

object WeakReferenceUtils {

    private val cache = ConcurrentHashMap<Any, WeakReference<*>>()

    fun <T> replace(key: Any, value: T): T? = cache.replace(key, WeakReference(value))?.get() as? T

}