package core.web3.datasource.alchemy

import core.web3.AbstractTest
import org.junit.jupiter.api.Assertions
import org.tinylog.kotlin.Logger
import java.math.BigDecimal
import kotlin.test.Test

class AlchemySourceTest : AbstractTest() {

    private val api = context.getAlchemySource()

    @Test
    fun `get address tokens`() = performTest {
        val walletAddress = "0x905BB5B5B1Fb3101fD76Da5B821B982012325C77"
        val balances = api.getBalances(walletAddress)
        val values = balances.map { balance ->
            val meta = api.getAsset(balance.address, walletAddress)!!
            meta to balance
        }
        values.forEach { value ->
            Logger.debug("balance :: {} -> {} : {}", value.second, value.first.name, value.first)
        }
        val matic = values.find { it.first.uid == "MATIC" }!!
        val maticBalance = matic.second.value.divide(BigDecimal.TEN.pow(matic.first.getDecimals()))
        Assertions.assertTrue(maticBalance > BigDecimal.ZERO)
    }

}