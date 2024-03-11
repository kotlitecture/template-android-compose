package core.dataflow.datasource.clipboard

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

class AndroidClipboardSource(private val app: Application) : ClipboardSource {

    private val changes: Flow<String?> = callbackFlow {
        val clipboardManager = app.getSystemService<ClipboardManager>()
        val callback = ClipboardManager.OnPrimaryClipChangedListener {
            val text = clipboardManager?.asText()
            channel.trySend(text)
        }
        clipboardManager?.addPrimaryClipChangedListener(callback)
        channel.trySend(clipboardManager?.asText())
        awaitClose {
            clipboardManager?.removePrimaryClipChangedListener(callback)
        }
    }.conflate()

    private fun ClipboardManager.asText(): String? {
        val clip = primaryClip ?: return null
        if (clip.itemCount > 0) {
            return clip.getItemAt(0)?.text?.toString()
        }
        return null
    }

    override fun getChanges(): Flow<String?> = changes

    override fun copy(text: String?, label: String?) {
        val clipboardManager = app.getSystemService<ClipboardManager>() ?: return
        val clip = ClipData.newPlainText(label.orEmpty(), text)
        clipboardManager.setPrimaryClip(clip)
    }

}