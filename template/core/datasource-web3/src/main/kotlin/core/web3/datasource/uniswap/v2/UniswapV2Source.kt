package core.web3.datasource.uniswap.v2

import core.essentials.http.HttpSource
import core.essentials.misc.extensions.infiniteFlow
import core.web3.datasource.uniswap.IUniswapSource
import core.web3.datasource.uniswap.model.Direction
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.PoolHourData
import core.web3.datasource.uniswap.model.Swap
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class UniswapV2Source(
    private val httpSource: HttpSource,
    private val pollInterval: Long = 12000L
) : IUniswapSource {

    private val ktor by lazy { httpSource.ktor }
    private val apiUrl = "https://api.thegraph.com/subgraphs/name/ianlapham/uniswap-v2-dev"

    private var lastSwapTime =
        ((System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)) / 1000).toBigInteger()

    private var lastPoolTime =
        ((System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)) / 1000).toBigInteger()

    override suspend fun getLatestSwaps(): Flow<List<Swap>> {
        return infiniteFlow("getSwaps") {
            val response: GetSwaps.Response = ktor
                .post {
                    url(apiUrl)
                    setBody(GetSwaps.latest(lastSwapTime))
                }
                .body()
            val swaps = response.data.swaps
                .filter { it.pair.isWethPair() }
            if (swaps.isNotEmpty()) {
                lastSwapTime = swaps.maxOf { it.timestamp }
                val wethSwaps = swaps
                    .filter { it.pair.isWethPair() }
                    .map { it.toModel() }
                    .map { it.normalize() }
                if (wethSwaps.isNotEmpty()) {
                    emit(wethSwaps)
                }
            }
            pollInterval
        }
    }

    override suspend fun getSwaps(poolId: String, count: Int, direction: Direction): List<Swap> {
        val response: GetSwaps.Response = ktor
            .post {
                url(apiUrl)
                setBody(GetSwaps.swaps(poolId, count, direction))
            }
            .body()
        return response.data.swaps
            .map { it.toModel() }
            .map { it.normalize() }
    }

    override suspend fun getLatestCandles(poolId: String, count: Int): List<PoolHourData> {
        return emptyList()
    }

    override suspend fun getPool(address: String): Pool {
        val response: GetPool.Response = ktor
            .post {
                url(apiUrl)
                setBody(GetPool.byAddress(address))
            }
            .body()
        return response.data.pool.normalize()
    }

    override suspend fun getLatestPools(): Flow<List<Pool>> {
        return infiniteFlow("getLatestPools") {
            val response: GetPools.Response = ktor
                .post {
                    url(apiUrl)
                    setBody(GetPools.latest(lastPoolTime))
                }
                .body()
            val pools = response.data.pools
                .filter { it.isWethPair() }
                .filter { it.createdAtTimestamp != null }
                .map { it.normalize() }
            if (pools.isNotEmpty()) {
                lastPoolTime = pools.maxOf { it.createdAtTimestamp!! }
                emit(pools)
            }
            30_000L
        }
    }

}