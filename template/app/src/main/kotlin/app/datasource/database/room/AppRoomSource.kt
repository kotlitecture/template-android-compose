package app.datasource.database.room

import android.app.Application
import androidx.room.Room
import androidx.room.withTransaction

class AppRoomSource(
    private val app: Application,
    private val databaseName: String = "db"
) {

    private val db by lazy {
        Room
            .databaseBuilder(
                klass = AppDatabase::class.java,
                name = databaseName,
                context = app,
            )
            .build()
    }

    val userDao by lazy { db.getUserDao() }

    suspend fun <R> withTransaction(block: suspend () -> R): R = db.withTransaction(block)

}