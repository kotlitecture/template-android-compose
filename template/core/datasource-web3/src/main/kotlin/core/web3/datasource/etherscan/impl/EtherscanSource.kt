package core.web3.datasource.etherscan.impl

import core.essentials.cache.ICacheSource
import core.essentials.http.HttpSource
import core.web3.datasource.etherscan.IEtherscanSource
import core.web3.datasource.etherscan.impl.data.GetContract
import core.web3.datasource.etherscan.impl.data.GetContractAbi
import core.web3.datasource.etherscan.impl.data.GetLastPrice
import core.web3.datasource.etherscan.impl.data.GetTransactionStatus
import core.web3.datasource.etherscan.impl.data.ModelMapper
import core.web3.model.TransactionState
import core.web3.model.eth.Abi
import core.web3.model.eth.ContractSource
import core.web3.model.eth.EthPrice
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class EtherscanSource(
    private val cacheSource: ICacheSource,
    private val httpSource: HttpSource,
    private val apiKey: String
) : IEtherscanSource {

    private val httpClient by lazy { httpSource.ktor }
    private val apiUrl: String = "https://api.etherscan.io/api"

    override suspend fun getContractSource(address: String): ContractSource? = cacheSource
        .get(ContractSource.CacheKey(address)) {
            val response = httpClient
                .get {
                    method = HttpMethod.Get
                    url(apiUrl)
                    parameter("apikey", apiKey)
                    parameter("module", "contract")
                    parameter("action", "getsourcecode")
                    parameter("address", address)
                }
                .body<GetContract.Response>()
            ModelMapper.toSource(address, response)
        }

    override suspend fun getTransactionState(hash: String): TransactionState {
        return try {
            val response = httpClient
                .get {
                    method = HttpMethod.Get
                    url(apiUrl)
                    parameter("apikey", apiKey)
                    parameter("module", "transaction")
                    parameter("action", "gettxreceiptstatus")
                    parameter("txhash", hash)
                }
                .body<GetTransactionStatus.Response>()
            ModelMapper.toStatus(response)
        } catch (e: Exception) {
            TransactionState.Unknown()
        }
    }

    override suspend fun getContractAbi(address: String): Abi? = cacheSource
        .get(Abi.CacheKey(address)) {
            val response = httpClient
                .get {
                    method = HttpMethod.Get
                    url(apiUrl)
                    parameter("apikey", apiKey)
                    parameter("module", "contract")
                    parameter("action", "getabi")
                    parameter("address", address)
                }
                .body<GetContractAbi.Response>()
            ModelMapper.toContractAbi(address, response.result)
        }

    override suspend fun getEthPrice(): EthPrice {
        val response = httpClient
            .get {
                method = HttpMethod.Get
                url(apiUrl)
                parameter("apikey", apiKey)
                parameter("module", "stats")
                parameter("action", "ethprice")
            }
            .body<GetLastPrice.Response>()
        return ModelMapper.toPrice(response)
    }

}