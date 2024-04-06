package app.datasource.database.objectbox

import android.annotation.SuppressLint
import androidx.paging.LegacyPagingSource
import androidx.paging.PagingSource
import io.objectbox.Box
import io.objectbox.android.ObjectBoxDataSource
import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers

abstract class BoxDao<T : BoxEntity>(
    protected val box: Box<T>
) {

    fun createOrUpdate(vararg entities: T) {
        box.put(*entities)
    }

    fun delete(vararg entities: T) {
        box.remove(*entities)
    }

    fun get(id: Long): T? {
        return box.get(id)
    }

    fun getAll(): List<T> {
        return box.all
    }

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