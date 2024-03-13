package app.userflow.update.googleplay.data

import com.google.android.play.core.appupdate.AppUpdateInfo

data class UpdateData(
    val type: Int,
    val info: AppUpdateInfo
)