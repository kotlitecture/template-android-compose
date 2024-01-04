package core.datasource.work.dao

import core.datasource.work.model.WorkFilter
import core.datasource.work.model.WorkModel
import kotlinx.coroutines.flow.Flow

interface IWorkDao {

    suspend fun get(id: String): WorkModel

    suspend fun save(model: WorkModel): WorkModel

    suspend fun getAll(filter: WorkFilter): Flow<List<WorkModel>>

}