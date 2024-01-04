package core.web3.datasource.uniswap.v3

import com.google.gson.annotations.SerializedName
import core.essentials.misc.extensions.safeDivide
import core.web3.datasource.uniswap.model.PoolHourData
import core.web3.datasource.uniswap.model.Query
import core.web3.datasource.uniswap.model.Token
import org.tinylog.Logger

object GetCandles {

    data class Response(
        @SerializedName("data")
        val data: PoolData
    )

    data class PoolData(
        @SerializedName("pool")
        val pool: PoolHourDataList
    )

    data class PoolHourDataList(
        @SerializedName("token0")
        val token0: Token,
        @SerializedName("token1")
        val token1: Token,
        @SerializedName("poolHourData")
        private val poolHourData: List<PoolHourData>
    ) {
        fun getTicks(): List<PoolHourData> {
            if (token0.isWeth()) {
                return poolHourData.map { tick ->
                    Logger.info("reverse tick :: {} - {}", token1.symbol, tick)
                    val priceRatio = tick.token1Price.safeDivide(tick.token0Price)
                    val volumeRatio = tick.volumeToken1.safeDivide(tick.volumeToken0)
                    tick.copy(
                        token0Price = tick.token1Price,
                        token1Price = tick.token0Price,
                        volumeToken0 = tick.volumeToken1,
                        volumeToken1 = tick.volumeToken0,
                        open = tick.open.multiply(priceRatio),
                        close = tick.close.multiply(priceRatio),
                        high = tick.high.multiply(priceRatio),
                        low = tick.low.multiply(priceRatio),
                        volumeUSD = tick.volumeUSD.multiply(volumeRatio),
                        liquidity = tick.liquidity.multiply(volumeRatio.toBigInteger())
                    )
                }
            }
            return poolHourData
        }
    }

    fun latest(address: String, count: Int): Query = Query(query = """
        {
            pool(id: "$address") {
                token0 {
                    id
                    symbol
                }
                token1 {
                    id
                    symbol
                }
                poolHourData(
                    orderBy: periodStartUnix
                    orderDirection: desc
                    first: $count
                ) {
                    id
                    open
                    close
                    high
                    low
                    feeGrowthGlobal0X128
                    feeGrowthGlobal1X128
                    liquidity
                    volumeUSD
                    periodStartUnix
                    txCount
                    token0Price
                    token1Price
                    volumeToken0
                    volumeToken1
                }
            }
        }
    """.trimIndent())

}