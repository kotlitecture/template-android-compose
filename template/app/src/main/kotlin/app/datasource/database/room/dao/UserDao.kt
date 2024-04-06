package app.datasource.database.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: User)

    @Update
    fun update(vararg users: User)

    @Delete
    fun delete(vararg user: User)

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun findByIds(userIds: LongArray): List<User>

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user")
    fun getAllPaginated(): PagingSource<Int, User>

}