package core.web3.datasource.alchemy.impl

import core.essentials.cache.ICacheSource
import core.essentials.http.HttpSource
import core.web3.datasource.alchemy.IAlchemySource
import core.web3.datasource.alchemy.impl.data.DataMapper
import core.web3.datasource.alchemy.impl.data.GetAsset
import core.web3.datasource.alchemy.impl.data.GetTokenBalances
import core.web3.model.Asset
import core.web3.model.AssetType
import core.web3.model.AssetValue
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class AlchemySource(
    apiKey: String,
    private val httpSource: HttpSource,
    private val cacheSource: ICacheSource
) : IAlchemySource {

    private val httpClient by lazy { httpSource.ktor }
    private val tokensApiUrl = "https://eth-mainnet.g.alchemy.com/v2/${apiKey}"

    override suspend fun getBalances(walletAddress: String): List<AssetValue> {
        val response = httpClient
            .post {
                url(tokensApiUrl)
                setBody(GetTokenBalances.Request(address = walletAddress))
            }
            .body<GetTokenBalances.Response>()
        return DataMapper.toBalances(response)
    }

    override suspend fun getAsset(contractAddress: String, walletAddress: String): Asset? {
        val key = Asset.CacheKey(id = null, address = contractAddress, type = AssetType.ERC20)
        return cacheSource.get(key) {
            val response = httpClient
                .post {
                    url(tokensApiUrl)
                    setBody(GetAsset.Request(address = contractAddress))
                }
                .body<GetAsset.Response>()
            DataMapper.toAsset(response, contractAddress)
        }
    }

}