package app.datasource.database.objectbox

import android.app.Application
import app.datasource.database.objectbox.entity.MyObjectBox

class AppObjectBoxSource(
    private val app: Application,
    private val databaseName: String = "db"
) {

    val boxStore by lazy {
        MyObjectBox.builder()
            .androidContext(app)
            .name(databaseName)
            .build()
    }

}