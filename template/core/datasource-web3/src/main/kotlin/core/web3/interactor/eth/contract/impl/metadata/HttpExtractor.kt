package core.web3.interactor.eth.contract.impl.metadata

import core.essentials.http.HttpSource
import io.ktor.client.request.*
import io.ktor.client.statement.*

class HttpExtractor(private val httpSource: HttpSource) : MetadataExtractor() {

    private val supported = listOf("http://", "https://")

    override fun canExtract(uri: String): Boolean = supported.any(uri::startsWith)

    override suspend fun extractJson(uri: String): String {
        return httpSource.ktor.get(uri).bodyAsText()
    }

}