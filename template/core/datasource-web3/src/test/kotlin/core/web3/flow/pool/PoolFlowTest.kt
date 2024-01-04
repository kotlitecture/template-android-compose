package core.web3.flow.pool

import core.web3.AbstractTest
import org.tinylog.kotlin.Logger
import kotlin.test.Test

class PoolFlowTest : AbstractTest() {

    @Test
    fun `test flow`() = performTest {
//        val address = "0x630fa62b1587e049274564f800ebf034ac01ae69"
//        val address = "0xd51c185d25855dcbe364208a2c06ca1a9ef511f8"
        val address = "0x26cf3c3de4cf64662811a0096bb51607aa49dc9c"
        val context = PoolFlowContext(
            wallet = context.getEthWallet(),
            tokenAddress = address,
            web3 = context
        )
        PoolFlowRunner.rate(context)
        Logger.debug("twitter :: {}", context.twitterUrl)
        Logger.debug("telegram :: {}", context.telegramUrl)
        Logger.debug("discord :: {}", context.discordUrl)
        Logger.debug("site :: {}", context.siteUrl)
        Logger.debug("context :: {}", context)
    }

}