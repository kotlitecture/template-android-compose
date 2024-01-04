package core.web3.datasource.uniswap

import core.web3.datasource.uniswap.model.Direction
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.PoolHourData
import core.web3.datasource.uniswap.model.Swap
import kotlinx.coroutines.flow.Flow

interface IUniswapSource {

    suspend fun getPool(address: String): Pool

    suspend fun getLatestSwaps(): Flow<List<Swap>>

    suspend fun getLatestPools(): Flow<List<Pool>>

    suspend fun getSwaps(poolId: String, count: Int, direction: Direction = Direction.Earliest): List<Swap>

    suspend fun getLatestCandles(poolId: String, count: Int): List<PoolHourData>

}