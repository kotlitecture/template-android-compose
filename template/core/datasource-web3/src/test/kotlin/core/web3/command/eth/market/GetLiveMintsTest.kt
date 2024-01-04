package core.web3.command.eth.market

import core.web3.AbstractTest
import core.web3.extensions.weiToEth
import core.web3.extensions.weiToGwei
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.junit.jupiter.api.Test
import org.tinylog.kotlin.Logger

class GetLiveMintsTest : AbstractTest() {

    @Test
    fun `get latest free mints`() = performTest {
        GetLiveMints().execute(context)
            .map { it.filter { it.isMintable() } }
            .filter { it.isNotEmpty() }
            .take(1)
            .collect { mints ->
                val mint = mints.first()
                Logger.debug(
                    """
                    =================== MINT =================== 
                    contract: ${mint.collectionName}
                    gas used: ${mint.gasUsed.weiToGwei()}
                    gas price: ${mint.gasPrice.weiToGwei()}
                    gas limit: ${mint.gasLimit.weiToGwei()}
                    tokens: ${mint.tokens}
                    minted: ${mint.tokens.last()}/${mint.maxSupply}
                    value: ${mint.value.weiToEth()}
                    function: ${mint.functionName}
                    contract: ${mint.collectionAddress}
                    block: ${mint.blockNumber}
                    =================== ---- ===================
                """.trimIndent()
                )
            }
    }

}