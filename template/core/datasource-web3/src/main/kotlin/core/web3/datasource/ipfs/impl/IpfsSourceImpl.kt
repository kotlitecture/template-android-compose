package core.web3.datasource.ipfs.impl

import com.google.gson.annotations.SerializedName
import core.essentials.http.HttpSource
import core.essentials.misc.extensions.rangeBetween
import core.web3.datasource.ipfs.IpfsSource
import core.web3.model.IpfsFile
import io.ktor.client.call.*
import io.ktor.client.plugins.retry
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*
import org.tinylog.Logger
import java.io.InputStream
import java.util.*

class IpfsSourceImpl(
    private val httpSource: HttpSource,
    private val gatewayUrl: String
) : IpfsSource {

    override fun getCatalogId(fileUrl: String): String? = runCatching {
        val pattern = "^ipfs://(.*?)/.*$".toRegex()
        val matchResult = pattern.find(fileUrl)
        matchResult?.groupValues?.get(1)
    }.getOrNull()

    override suspend fun getFiles(catalogId: String): List<IpfsFile> {
        val response = getLsResponse(catalogId)
        val catalog: LsResponse = response.body()
        val files = catalog.objects.firstOrNull()?.links ?: emptyList()
        return files.sortedBy { it.size }
    }

    override suspend fun getFilesExperimental(catalogId: String): List<IpfsFile> {
        return runCatching {
            val response = getLsResponse(catalogId)
            val files = ArrayList<IpfsFile>(10000)
            var name: String? = null
            var size: Long? = null
            val namePrefix = "\"Name\":\""
            val nameSuffix = "\","
            val sizePrefix = "\"Size\":"
            val sizeSuffix = ","
            response.body<InputStream>().bufferedReader().forEachLine { line ->
                var offset = 0
                while (true) {
                    val nameRef =
                        name ?: line.rangeBetween(namePrefix, nameSuffix, offset)?.let { range ->
                            offset = range.last
                            line.substring(range.first, range.last)
                        }
                    val sizeRef =
                        size ?: line.rangeBetween(sizePrefix, sizeSuffix, offset)?.let { range ->
                            offset = range.last
                            line.substring(range.first, range.last).toLongOrNull()
                        }
                    if (nameRef != null && sizeRef != null) {
                        files.add(IpfsFile(nameRef, sizeRef))
                        name = null
                        size = null
                    } else {
                        break
                    }
                }
            }
            files.sortedBy { it.size }
        }.getOrElse { getFiles(catalogId) }
    }

    private suspend fun getLsResponse(catalogId: String): HttpResponse {
        val url = "${gatewayUrl}/api/v0/ls?arg=${catalogId}"
        Logger.debug("url :: {}", url)
        return httpSource.ktor.get(url) {
            retry {
                retryOnException(2, true)
            }
        }
    }

    private data class LsResponse(
        @SerializedName("Objects") val objects: List<LsObject>
    )

    private data class LsObject(
        @SerializedName("Links") val links: List<IpfsFile>
    )

}