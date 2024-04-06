package app.datasource.database.room

import android.app.Application
import androidx.room.Room
import androidx.room.withTransaction
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

    suspend fun <R> withTransaction(block: suspend () -> R): R = db.withTransaction(block)

    fun getUserDao(): UserDao = db.getUserDao()

}