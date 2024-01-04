package core.web3.interactor.eth.contract.impl.metadata

import io.ktor.util.*

class Base64Extractor : MetadataExtractor() {

    override fun canExtract(uri: String): Boolean = uri.startsWith("data:")

    override suspend fun extractJson(uri: String): String {
        val jsonBase64 = uri.substring(uri.indexOf(',') + 1)
        return jsonBase64.decodeBase64String()
    }

}