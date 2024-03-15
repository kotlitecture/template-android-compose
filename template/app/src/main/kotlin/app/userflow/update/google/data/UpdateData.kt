package app.userflow.update.google.data

import com.google.android.play.core.appupdate.AppUpdateInfo

data class UpdateData(
    val type: Int,
    val info: AppUpdateInfo
)