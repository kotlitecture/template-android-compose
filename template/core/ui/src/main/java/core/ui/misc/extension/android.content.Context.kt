package core.ui.misc.extension

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Vibrator
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentActivity
import core.ui.R
import org.tinylog.Logger

fun Context.yesNo(yes: Boolean): String {
    return if (yes) getString(R.string.button_yes) else getString(R.string.button_no)
}

fun Context.findActivity(): FragmentActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) return context
        context = context.baseContext
    }
    return null
}

fun Context.requireActivity(): FragmentActivity {
    return findActivity()!!
}

fun Context.safeIntent(intent: Intent) {
    if (this !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

fun Context.openUrl(url: String, chooserTitle: CharSequence = "") {
    val intent = Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse(url))
    val chooserIntent = Intent.createChooser(intent, chooserTitle)
    safeIntent(chooserIntent)
}

fun Context.openUrlInChromeTab(url: String): Boolean {
    val customTabsIntent = CustomTabsIntent.Builder()
        .build()
    return try {
        if (this !is Activity) {
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        customTabsIntent.launchUrl(this, Uri.parse(url))
        true
    } catch (e: Exception) {
        Logger.error(e, "openUrlInChromeTab")
        openUrl(url)
        false
    }
}

fun Context.copyToClipboard(
    text: String,
    label: String? = null,
    onSuccessAction: (() -> Unit)? = null
) {
    val service = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager ?: return
    val clip = ClipData.newPlainText(label.orEmpty(), text)
    service.setPrimaryClip(clip)
    onSuccessAction?.invoke()
}

fun Context.vibrateOk() = vibrate(longArrayOf(0, 20))
fun Context.vibrateHaptic() = vibrate(longArrayOf(0, 10))
fun Context.vibrateWrong() = vibrate(longArrayOf(0, 40, 100, 40))
fun Context.vibrate(pattern: LongArray, repeatMode: Int = -1): Boolean {
    return try {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        vibrator?.run {
            vibrate(pattern, repeatMode)
            true
        } ?: run { false }
    } catch (th: Throwable) {
        false
    }
}

private const val SHORT_VIBRATION = 50L
private const val LONG_VIBRATION = 200L
private const val PAUSE = 100L

fun Context.vibrateMelody1() = vibrate(
    longArrayOf(LONG_VIBRATION, PAUSE, PAUSE)
)