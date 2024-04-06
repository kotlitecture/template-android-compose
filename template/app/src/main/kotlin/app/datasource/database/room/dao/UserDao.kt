package app.datasource.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.datasource.database.room.entity.User

/**
 * Just an example of typical dao to work with the [User] domain.
 *
 * Anatomy of a DAO: https://developer.android.com/training/data-storage/room/accessing-data#kotlin.
 */
@Dao
interface UserDao {

    @Insert
    fun create(vararg users: User)

    @Update
    fun update(vararg users: User)

    @Delete
    fun delete(vararg user: User)

    @Query("SELECT * FROM user WHERE id = :id LIMIT 1")
    fun get(id: Long): User?

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user")
    fun getAllPaginated(): PagingSource<Int, User>

}