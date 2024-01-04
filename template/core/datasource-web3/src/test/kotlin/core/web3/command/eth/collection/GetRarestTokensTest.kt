package core.web3.command.eth.collection

import core.web3.AbstractTest
import core.web3.command.eth.collection.GetRarestTokens
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GetRarestTokensTest : AbstractTest() {

    private val expectedTokens = 17
    private val contract = CONTRACT_GOBLINTOWN
//  private val contract = "0x894a19ccf5b1137507a45bb981e2c418a73651b6"
//  private val contract = "0x2fdca7224a77335d76a5d5882d6441473aed2e01"
//  private val contract = "0xc589770757cd0d372c54568bf7e5e1d56b958015" // https endpoints

    @Test
    fun `get rarest tokens`() = performTest {
        val rarest = GetRarestTokens(
            withCollectionAddress = contract,
            withExperimentalApi = false
        ).execute(context)
        Assertions.assertEquals(expectedTokens, rarest.size)
    }

    @Test
    fun `get rarest tokens - experimental`() = performTest {
        val rarest = GetRarestTokens(
            withCollectionAddress = contract,
            withExperimentalApi = true
        ).execute(context)
        Assertions.assertEquals(expectedTokens, rarest.size)
    }

}