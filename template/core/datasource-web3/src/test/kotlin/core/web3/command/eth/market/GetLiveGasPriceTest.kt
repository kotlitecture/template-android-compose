package core.web3.command.eth.market

import core.web3.AbstractTest
import core.web3.command.eth.market.GetLiveGasPrice
import kotlinx.coroutines.flow.take
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class GetLiveGasPriceTest : AbstractTest() {

    @Test
    fun `get live gas price`() = performTest {
        GetLiveGasPrice().execute(context)
            .take(1)
            .collect { price ->
                Assertions.assertTrue(price.price != BigInteger.ZERO)
            }
    }

}