package core.web3.datasource.uniswap.v2

import com.google.gson.annotations.SerializedName
import core.web3.datasource.uniswap.model.Direction
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.Query
import core.web3.datasource.uniswap.model.Swap
import core.web3.datasource.uniswap.model.Transaction
import java.math.BigDecimal
import java.math.BigInteger

object GetSwaps {

    data class Response(
        @SerializedName("data")
        val data: Swaps
    )

    data class Swaps(
        @SerializedName("swaps")
        val swaps: List<SwapData>
    )

    fun latest(timestamp: BigInteger): Query = Query(query = """
      {
        swaps(
        orderBy: timestamp,
        orderDirection: desc,
        where: {
          timestamp_gt: $timestamp
        }) {
        pair {
        token0 {
        id
        symbol
        }
        token1 {
        id
        symbol
        }
        id
        reserveETH
        reserveUSD
        }
        from
        to
        amount0In
        amount0Out
        amount1In
        amount1Out
        id
        timestamp
        transaction {
          id
          blockNumber
          timestamp
        }
        }
        }
    """.trimIndent())

    fun swaps(poolId: String, count: Int, direction: Direction): Query = Query(query = """
      {
        swaps(
        orderBy: timestamp,
        orderDirection: ${direction.order},
        ${direction.prefix}: $count
        where: {
            pair_: {
                id: "$poolId"
            }
        }) {
        pair {
        token0 {
        id
        symbol
        }
        token1 {
        id
        symbol
        }
        id
        reserveETH
        reserveUSD
        }
        from
        to
        amount0In
        amount0Out
        amount1In
        amount1Out
        id
        timestamp
        transaction {
          id
          blockNumber
          timestamp
        }
        }
        }
    """.trimIndent())

    data class SwapData(
        val id: String,
        val pair: Pool,
        val transaction: Transaction,
        val timestamp: BigInteger,
        val amount0In: BigDecimal,
        val amount0Out: BigDecimal,
        val amount1In: BigDecimal,
        val amount1Out: BigDecimal,
        val from: String,
        val to: String,
    ) {
        fun toModel(): Swap {
            val amount0 = if (amount0In != BigDecimal.ZERO) amount0In else amount0Out.unaryMinus()
            val amount1 = if (amount1In != BigDecimal.ZERO) amount1In else amount1Out.unaryMinus()
            return Swap(
                id = id,
                pool = pair,
                transaction = transaction,
                timestamp = timestamp,
                amount0 = amount0,
                amount1 = amount1,
                sender = from,
                recipient = to
            )
        }
    }

}