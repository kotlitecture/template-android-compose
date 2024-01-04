package core.ui.app

import android.content.Intent
import java.util.concurrent.atomic.AtomicBoolean

data class AppIntent(private val intent: Intent?) {

    private val consumed = AtomicBoolean(false)

    fun consume(): Intent? {
        return if (consumed.compareAndSet(false, true)) {
            intent
        } else {
            null
        }
    }

}