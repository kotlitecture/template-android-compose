package core.web3.datasource.uniswap

import core.web3.AbstractTest
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.Swap
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.junit.jupiter.api.Assertions
import org.tinylog.Logger
import kotlin.test.Test

abstract class UniswapSourceTest : AbstractTest() {

    protected abstract val api: IUniswapSource

    protected abstract val poolId: String

    @Test
    fun `get latest swaps`() = performTest {
        val swaps = mutableListOf<Swap>()
        api.getLatestSwaps()
            .first()
            .reversed()
            .let(swaps::addAll)
        swaps.reverse()
        check(swaps)
    }

    @Test
    fun `get latest swaps with limit`() = performTest {
        val swaps = api.getSwaps(poolId, 10).toMutableList()
        swaps.forEach { swap -> Assertions.assertEquals(poolId, swap.pool.id) }
        swaps.reverse()
        check(swaps)
    }

    private fun check(swaps: List<Swap>) {
        Assertions.assertTrue(swaps.isNotEmpty())
        Assertions.assertEquals(swaps.map { it.id }.distinct().size, swaps.size)
        var prevTimestamp = System.currentTimeMillis()
        swaps.forEach { swap ->
            val time = swap.timestamp.toLong() * 1000
            Assertions.assertNotNull(swap.amount0)
            Assertions.assertNotNull(swap.amount1)
            Assertions.assertNotNull(swap.pool.token0)
            Assertions.assertNotNull(swap.pool.token1)
            Logger.debug("check :: {} - {}", prevTimestamp, time)
            Assertions.assertTrue(time <= prevTimestamp)
            prevTimestamp = time
        }
    }

    @Test
    fun `get latest pools`() = performTest {
        val pools = mutableListOf<Pool>()
        api.getLatestPools()
            .take(1)
            .map { it.reversed() }
            .collectLatest(pools::addAll)

        pools.reverse()

        Assertions.assertTrue(pools.isNotEmpty())
        Assertions.assertEquals(pools.map { it.id }.distinct().size, pools.size)
        var prevTimestamp = System.currentTimeMillis()
        pools.forEach { pool ->
            val time = pool.createdAtTimestamp!!.toLong() * 1000
            Assertions.assertNotNull(pool.token0.id)
            Assertions.assertNotNull(pool.token1.id)
            Assertions.assertNotNull(pool.token0Price)
            Assertions.assertNotNull(pool.token1Price)
            Assertions.assertNotNull(pool.txCount)
            Assertions.assertNotNull(pool.totalValueLockedETH)
            Assertions.assertNotNull(pool.totalValueLockedUSD)
            Logger.debug("check :: {} - {}", prevTimestamp, time)
            Assertions.assertTrue(time <= prevTimestamp)
            prevTimestamp = time
        }
    }

    @Test
    open fun `get candles`() = performTest {
        api.getLatestCandles(poolId, 1)
            .let { candles ->
                Assertions.assertEquals(1, candles.size)
                candles.forEach { candle ->
                    Assertions.assertNotNull(candle.id)
                }
            }
    }

    @Test
    fun `get pool`() = performTest {
        val address = poolId
        val pool = api.getPool(address)
        Assertions.assertEquals(address, pool.id)
    }

}