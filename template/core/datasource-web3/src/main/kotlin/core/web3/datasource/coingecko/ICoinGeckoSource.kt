package core.web3.datasource.coingecko

import java.math.BigDecimal

interface ICoinGeckoSource {

    suspend fun getPrice(coin: String, currency: String): BigDecimal

}