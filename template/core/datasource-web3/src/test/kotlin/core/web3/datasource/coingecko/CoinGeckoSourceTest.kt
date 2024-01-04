package core.web3.datasource.coingecko

import core.web3.AbstractTest
import org.junit.jupiter.api.Assertions
import java.math.BigDecimal
import kotlin.test.Test

class CoinGeckoSourceTest : AbstractTest() {

    private val api: ICoinGeckoSource = context.getCoinGeckoSource()

    @Test
    fun `get eth price in usd`() = performTest {
        val coin = "ethereum"
        val currency = "usd"
        val price = api.getPrice(coin, currency)
        Assertions.assertNotEquals(BigDecimal.ZERO, price)
    }

}