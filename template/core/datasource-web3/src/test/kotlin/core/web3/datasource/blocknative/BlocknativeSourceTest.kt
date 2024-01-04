package core.web3.datasource.blocknative

import core.web3.AbstractTest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import org.tinylog.Logger
import kotlin.test.Ignore
import kotlin.test.Test

class BlocknativeSourceTest : AbstractTest() {

    private val api: IBlocknativeSource = context.getBlocknativeSource()

    @Test
    @Ignore("only with VPN")
    fun `when get contract changes`() = performTest {
        val address = "0x0c9663115b36fa95d18e71d59054117bcb0342ef" // XTREME PIXELS
        api.getChanges(address)
            .mapNotNull { it.event?.transaction }
            .collect { tx ->
                val params =
                    tx.internalTransactions?.firstNotNullOfOrNull { it.contractCall }?.params
                Logger.debug(
                    "status={}, token={}, to={}",
                    tx.status,
                    params?.get("_tokenId"),
                    params?.get("_to")
                )
            }
    }

    @Test
    @Ignore("only with VPN")
    fun `when get uniswap changes`() = performTest {
        val address = "0x7a250d5630b4cf539739df2c5dacb4c659f2488d"
        api.getChanges(address)
            .take(1)
            .mapNotNull { it.event?.transaction }
            .collect { tx ->
                val netBalanceChange = tx.netBalanceChanges?.getOrNull(0)
                val assets = netBalanceChange?.balanceChanges
                if (!assets.isNullOrEmpty()) {
                    Logger.debug(
                        "\nstatus={}\ndelta={}\nasset={}\n",
                        tx.status,
                        assets.map { it.delta },
                        assets.map { it.asset.symbol },
                    )
                }
            }
    }

}