package core.web3.interactor.eth.contract.impl.metadata

import core.essentials.http.HttpSource
import io.ktor.client.request.*
import io.ktor.client.statement.*

class IpfsExtractor(private val httpSource: HttpSource) : MetadataExtractor() {

    private val prefix = "ipfs://"

    override fun canExtract(uri: String): Boolean = uri.startsWith(prefix)

    override suspend fun extractJson(uri: String): String {
        return httpSource.ktor.get(getHttpUrl(uri)).bodyAsText()
    }

    private fun getHttpUrl(uri: String): String {
        val hash = uri.substring(prefix.length)
        return "http://ipfs.io/ipfs/${hash}"
    }

}