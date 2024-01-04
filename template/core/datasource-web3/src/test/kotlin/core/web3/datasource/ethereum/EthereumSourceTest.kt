package core.web3.datasource.ethereum

import core.web3.AbstractTest
import core.web3.extensions.weiToEth
import core.web3.model.eth.Transfer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import org.junit.jupiter.api.Assertions
import org.tinylog.kotlin.Logger
import java.math.BigInteger
import kotlin.test.Test

class EthereumSourceTest : AbstractTest() {

    private val api = context.getEthereumSource()

    @Test
    fun `get block by number`() = performTest {
        val block1 = api.getBlockLive().first()
        val block2 = api.getBlock(block1.number)
        Assertions.assertEquals(block1, block2)
        Assertions.assertSame(block1, block2)

        val block3 = api.getBlock(block1.number.minus(BigInteger.ONE))!!
        val block4 = api.getBlock(block3.number)
        Assertions.assertSame(block3, block4)
    }

    @Test
    fun `get block`() = performTest {
        api.getBlockLive()
            .take(1)
            .collectLatest { block ->
                Logger.debug(block)
            }
    }

    @Test
    fun `get balance`() = performTest {
        // existing wallet
        var balance = api.getBalance("0x905BB5B5B1Fb3101fD76Da5B821B982012325C77")
        Assertions.assertNotEquals(BigInteger.ZERO, balance)
        Logger.debug("balance : {}", balance.weiToEth())

        // not existing wallet
        balance = api.getBalance("0x905BB5B5B1Fb3101fD76Da5B821B982012325C76")
        Assertions.assertEquals(BigInteger.ZERO, balance)
    }

    @Test
    fun `get balance of multiple wallets`() = performTest {
        val wallets = mutableListOf<String>()
        repeat(10) {
            wallets.add("0x905BB5B5B1Fb3101fD76Da5B821B982012325C77")
            wallets.add("0xa65d4909f694EAE5325990f8be93c06f949150DD")
        }
        val balances = api.getBalance(wallets)
        Assertions.assertEquals(wallets.size, balances.size)
        Logger.debug("balances :: {}", balances.map { it.weiToEth() })
    }

    @Test
    fun `get gas price`() = performTest {
        api.getGasPriceLive()
            .take(1)
            .collect { price ->
                Logger.debug(price)
                Assertions.assertTrue(price.price > BigInteger.ZERO)
            }
    }

    @Test
    fun `get tokens transfers and ensure they are unique`() = performTest {
        val groupsCount = 2
        val groups = mutableListOf<List<Transfer>>()
        api.getTokenTransfers()
            .take(groupsCount)
            .collect { mints ->
                groups.add(mints)
            }

        Assertions.assertEquals(groupsCount, groups.size)

        // all groups are unique
        groups.forEachIndexed { index, next ->
            val prev = groups.getOrNull(index - 1)
            if (prev != null) {
                Assertions.assertEquals(next, next.minus(prev))
            }
        }

        // each new group has the same block
        groups.forEach { group ->
            val nextBlocks = group
                .map { it.events }
                .map { it.map { it.blockNumber } }
                .flatten()
                .distinct()
            Assertions.assertEquals(1, nextBlocks.size)
        }

        // each new group has block next to previous
        val blocks = groups.map { group ->
            group
                .asSequence()
                .map { it.events }
                .map { it.map { it.blockNumber } }
                .flatten()
                .distinct()
                .first()
        }
        Assertions.assertEquals(groupsCount, blocks.size)
        Assertions.assertEquals(blocks, blocks.distinct())
        Assertions.assertEquals(blocks, blocks.sorted())
        Logger.debug("blocks :: {}", blocks)
    }

    @Test
    fun `get transaction by correct hash`() = performTest {
        val hash = "0x9c0974f4f073a1efafd1cc09f698ef425c480b12c4d7fe01c4bfe9f9ad004c92"
        val tx = api.getTx(hash)!!
        Logger.debug("tx={}", tx)
    }

    @Test
    fun `get transaction by wrong hash`() = performTest {
        val hash = "ABC"
        val tx = api.getTx(hash)
        Assertions.assertNull(tx)
    }

    @Test
    fun `get transaction state`() = performTest {
        val hash = "0xe0b2c07988f7fb42dcf56dd72a3fd6781a50544b039dfa175cacc1b02faba896"
        val tx = api.getTx(hash)
        Logger.debug("hash :: {}", tx?.status)
    }

}