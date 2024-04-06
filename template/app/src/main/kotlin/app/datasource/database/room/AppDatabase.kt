package app.datasource.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import app.datasource.database.room.dao.UserDao
import app.datasource.database.room.entity.User

@Database(
    entities = [
        User::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}