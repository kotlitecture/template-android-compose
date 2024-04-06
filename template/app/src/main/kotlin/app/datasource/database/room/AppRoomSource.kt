package app.datasource.database.room

import android.app.Application
import androidx.room.Room
import app.datasource.database.room.dao.UserDao

class AppRoomSource(
    private val app: Application,
    private val databaseName: String = "db"
) {

    val db by lazy {
        Room
            .databaseBuilder(
                klass = AppDatabase::class.java,
                name = databaseName,
                context = app,
            )
            .build()
    }

    fun getUserDao(): UserDao = db.getUserDao()

}