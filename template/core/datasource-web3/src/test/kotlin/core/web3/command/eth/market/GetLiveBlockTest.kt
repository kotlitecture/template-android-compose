package core.web3.command.eth.market

import core.web3.AbstractTest
import core.web3.command.eth.market.GetLiveBlock
import kotlinx.coroutines.flow.take
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger

class GetLiveBlockTest : AbstractTest() {

    @Test
    fun `get live block`() = performTest {
        GetLiveBlock().execute(context)
            .take(1)
            .collect { block ->
                Assertions.assertTrue(block.number != BigInteger.ZERO)
            }
    }

}