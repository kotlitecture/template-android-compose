package app.userflow.update.google

import app.userflow.update.google.data.UpdateConfig
import app.userflow.update.google.data.UpdateData
import core.ui.state.StoreObject
import core.ui.state.StoreState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleUpdateState @Inject constructor() : StoreState() {

    val configStore = StoreObject(UpdateConfig())
    val dataStore = StoreObject<UpdateData>()

}