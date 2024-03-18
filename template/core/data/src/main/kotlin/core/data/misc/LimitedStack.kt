package core.data.misc

import java.util.concurrent.ConcurrentLinkedDeque

class LimitedStack<O>(private val capacity: Int) : ConcurrentLinkedDeque<O>() {

    override fun add(element: O): Boolean {
        addFirst(element)
        if (size > capacity) {
            removeLast()
        }
        return true
    }

}