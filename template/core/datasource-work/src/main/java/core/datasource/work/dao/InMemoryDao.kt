package core.datasource.work.dao

import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date
import java.util.concurrent.ConcurrentHashMap

class InMemoryDao : IWorkDao {

    private val works = ConcurrentHashMap<String, WorkModel>()
    private val worksSet = LinkedHashSet<String>()

    override suspend fun get(id: String): WorkModel {
        return works[id]!!
    }

    override suspend fun save(model: WorkModel): WorkModel {
        val updated = model.copy(updateDate = Date())
        works[model.uid] = updated
        worksSet.add(model.uid)
        return updated
    }

    override suspend fun getAll(filter: WorkFilter): Flow<List<WorkModel>> {
        return flowOf(
            works.values
                .filter { filter.stateIn.isEmpty() || filter.stateIn.contains(it.state) }
                .filter { it.parentId == filter.parentId || filter.includeChildren }
                .sortedBy { worksSet.indexOf(it.uid) }
        )
    }

}