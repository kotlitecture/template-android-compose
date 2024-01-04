package core.datasource

interface IDataSource {

    suspend fun init() = Unit

}