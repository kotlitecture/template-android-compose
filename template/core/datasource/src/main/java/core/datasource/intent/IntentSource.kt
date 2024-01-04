package core.datasource.intent

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.core.content.FileProvider
import core.data.misc.extensions.grantUriPermissions
import core.data.misc.extensions.takePersistableUriPermission
import core.essentials.misc.utils.IdUtils
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.io.File
import javax.inject.Inject

@ActivityRetainedScoped
class IntentSource @Inject constructor(
    private val app: Application
) {

    lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>
    lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private var activityResultCallback: (activityResult: ActivityResult) -> Unit = {}
    private var permissionsCallback: (permissions: Map<String, Boolean>) -> Unit = {}
    private var intentSenderCallback: (activityResult: ActivityResult) -> Unit = {}

    @Composable
    @NonRestartableComposable
    fun attachToActivityScope() {
        permissionsLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            permissionsCallback.invoke(it)
            permissionsCallback = {}
        }
        activityResultLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            activityResultCallback.invoke(it)
            activityResultCallback = {}
        }
        intentSenderLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {
            intentSenderCallback.invoke(it)
            intentSenderCallback = {}
        }
    }

    fun withPermissions(
        vararg permissions: String,
        callback: (grantResults: Map<String, Boolean>) -> Unit
    ) {
        permissionsCallback = callback
        permissionsLauncher.launch(permissions.toList().toTypedArray())
    }

    fun withFile(mediaType: String = "*/*", persistable: Boolean = false, callback: (uri: Uri) -> Unit) {
        val request = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            if (persistable) {
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            }
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mediaType
        }
        launch(request) { result ->
            result.data?.data?.let { uri ->
                if (persistable) {
                    app.takePersistableUriPermission(uri)
                }
                callback.invoke(uri)
            }
        }
    }

    fun withNewFile(name: String?, persistable: Boolean, callback: (uri: Uri) -> Unit) {
        val request = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            if (persistable) {
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            }
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_TITLE, name)
            type = "*/*"
        }
        launch(request) { result ->
            result.data?.data?.let { uri ->
                if (persistable) {
                    app.takePersistableUriPermission(uri)
                }
                callback.invoke(uri)
            }
        }
    }

    fun withPhoto(callback: (uri: Uri) -> Unit) {
        val packageName = app.packageName
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val dir = app.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!
        val file = File(dir, "${IdUtils.timeBasedId("PHOTO")}.jpg")
        val uri = FileProvider.getUriForFile(app, "${packageName}.file_provider", file)
        app.grantUriPermissions(uri)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        withPermissions(Manifest.permission.CAMERA) {
            launch(intent) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    callback.invoke(uri)
                }
            }
        }
    }

    fun withVideoRecord(callback: (uri: Uri) -> Unit) {
        val packageName = app.packageName
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val dir = app.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!
        val file = File(dir, "${IdUtils.timeBasedId("RECORD")}.jpg")
        val uri = FileProvider.getUriForFile(app, "${packageName}.file_provider", file)
        app.grantUriPermissions(uri)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        withPermissions(Manifest.permission.CAMERA) {
            launch(intent) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    callback.invoke(uri)
                }
            }
        }
    }

    private fun launch(intent: Intent, callback: (result: ActivityResult) -> Unit) {
        activityResultCallback = callback
        activityResultLauncher.launch(intent)
    }

}