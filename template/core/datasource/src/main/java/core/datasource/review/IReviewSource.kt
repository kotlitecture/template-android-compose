package core.datasource.review

import core.datasource.IDataSource
import kotlinx.coroutines.flow.Flow

interface IReviewSource : IDataSource {

    suspend fun isAvailable(): Flow<Boolean>

    suspend fun startReview()

}