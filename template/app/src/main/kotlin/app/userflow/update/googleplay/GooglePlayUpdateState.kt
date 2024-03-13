package app.userflow.update.googleplay

import app.userflow.update.googleplay.data.UpdateConfig
import app.userflow.update.googleplay.data.UpdateData
import core.ui.state.StoreObject
import core.ui.state.StoreState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GooglePlayUpdateState @Inject constructor() : StoreState() {

    val configStore = StoreObject(UpdateConfig())
    val dataStore = StoreObject<UpdateData>()

}