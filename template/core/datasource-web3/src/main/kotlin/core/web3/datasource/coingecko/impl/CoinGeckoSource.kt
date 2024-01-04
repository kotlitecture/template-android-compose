package core.web3.datasource.coingecko.impl

import core.essentials.cache.ICacheSource
import core.web3.datasource.coingecko.ICoinGeckoSource
import core.web3.datasource.coingecko.impl.data.GetPrice
import core.essentials.http.HttpSource
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import java.math.BigDecimal

class CoinGeckoSource(
    private val cacheSource: ICacheSource,
    private val httpSource: HttpSource
) : ICoinGeckoSource {

    private val httpClient by lazy { httpSource.ktor }
    private val apiUrl: String = "https://api.coingecko.com/api/v3"

    override suspend fun getPrice(coin: String, currency: String): BigDecimal {
        val prices = getPrices(listOf(coin), listOf(currency))
        val price = prices.values.first()
        return price.values.first()
    }

    private suspend fun getPrices(
        coins: List<String>,
        currencies: List<String>
    ): GetPrice.Response {
        return httpClient
            .get {
                method = HttpMethod.Get
                url {
                    url(apiUrl)
                    appendPathSegments("simple/price")
                }
                parameter("ids", coins.joinToString(","))
                parameter("vs_currencies", currencies.joinToString(","))
            }
            .body()
    }
}