package core.datasource.firebase.config

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import core.data.startup.DependencyInitializer

class FirebaseRemoteConfigInitializer : DependencyInitializer<FirebaseRemoteConfig>() {

    override fun initialize(context: Context): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }

}