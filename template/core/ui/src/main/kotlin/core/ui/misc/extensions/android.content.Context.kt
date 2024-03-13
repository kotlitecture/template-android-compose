package core.ui.misc.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity

fun Context.findActivity(): FragmentActivity? {
    if (this is FragmentActivity) return this
    var context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) return context
        context = context.baseContext
    }
    return null
}