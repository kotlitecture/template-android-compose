package app.showcases.datasource.paging.basic

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay

class BasicPagingSource(private val loadDelay: Long = 500) : PagingSource<Int, String>() {

    private val allItems = (1..300).map { "Item-$it" }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        val offset = params.key ?: 0
        val pageSize = params.loadSize
        val items = allItems.drop(offset).take(pageSize)
        delay(loadDelay)
        return LoadResult.Page(
            data = items,
            prevKey = (offset - pageSize).takeIf { it >= 0 },
            nextKey = (offset + items.size).takeIf { it != offset }
        )
    }
}