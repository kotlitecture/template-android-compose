package app.datasource.database.objectbox

import android.annotation.SuppressLint
import androidx.paging.LegacyPagingSource
import androidx.paging.PagingSource
import io.objectbox.Box
import io.objectbox.android.ObjectBoxDataSource
import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers

/**
 * Represents a base DAO (Data Access Object) for interacting with ObjectBox entities.
 *
 * @param T The type of entities handled by this DAO, must be a subtype of BoxEntity.
 * @property box The ObjectBox box containing entities of type T.
 */
abstract class BoxDao<T : BoxEntity>(
    protected val box: Box<T>
) {

    /**
     * Inserts or updates the specified entities into the database.
     *
     * @param entities The entities to insert or update.
     */
    fun createOrUpdate(vararg entities: T) {
        box.put(*entities)
    }

    /**
     * Deletes the specified entities from the database.
     *
     * @param entities The entities to delete.
     */
    fun delete(vararg entities: T) {
        box.remove(*entities)
    }

    /**
     * Retrieves the entity with the specified ID from the database.
     *
     * @param id The ID of the entity to retrieve.
     * @return The entity with the specified ID, or null if not found.
     */
    fun get(id: Long): T? {
        return box.get(id)
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return A list of all entities in the database.
     */
    fun getAll(): List<T> {
        return box.all
    }

    /**
     * Retrieves entities from the database in a paginated manner.
     *
     * @return A PagingSource that provides access to paginated data.
     */
    fun getAllPaginated(): PagingSource<Int, T> {
        val query = box.query().build()
        return createPagingSource(query)
    }

    @SuppressLint("RestrictedApi")
    protected fun createPagingSource(query: Query<T>): PagingSource<Int, T> {
        val dataSource = ObjectBoxDataSource(query)
        return LegacyPagingSource(Dispatchers.IO, dataSource)
    }

}