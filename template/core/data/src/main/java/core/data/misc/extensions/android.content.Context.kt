package core.data.misc.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import org.tinylog.Logger

fun Context.takePersistableUriPermission(uri: Uri): Uri? {
    return try {
        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
        contentResolver.takePersistableUriPermission(uri, takeFlags)
        Logger.debug("takePersistableUriPermission :: success :: {}", uri)
        uri
    } catch (th: Throwable) {
        Logger.debug("takePersistableUriPermission :: error :: {} - {}", uri, th.message)
        uri
    }
}

fun Context.grantUriPermissions(uri: Uri): Uri? {
    return try {
        val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
        grantUriPermission(packageName, uri, takeFlags)
        Logger.debug("grantUriPermissions :: success :: {}", uri)
        uri
    } catch (th: Throwable) {
        Logger.debug("grantUriPermissions :: error :: {} - {}", uri, th.message)
        null
    }
}

fun Context.getPersistableUri(uri: String): Uri? = runCatching { grantUriPermissions(uri.toUri()) }
    .getOrNull()