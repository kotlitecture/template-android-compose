package core.web3.datasource.ipfs

import core.web3.model.IpfsFile

interface IpfsSource {

    fun getCatalogId(fileUrl: String): String?

    suspend fun getFiles(catalogId: String): List<IpfsFile>

    suspend fun getFilesExperimental(catalogId: String): List<IpfsFile>

}