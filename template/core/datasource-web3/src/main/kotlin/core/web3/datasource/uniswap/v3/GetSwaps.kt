package core.web3.datasource.uniswap.v3

import com.google.gson.annotations.SerializedName
import core.web3.datasource.uniswap.model.Direction
import core.web3.datasource.uniswap.model.Query
import core.web3.datasource.uniswap.model.Swap
import java.math.BigInteger

object GetSwaps {

    data class Response(
        @SerializedName("data")
        val data: Swaps
    )

    data class Swaps(
        @SerializedName("swaps")
        val swaps: List<Swap>
    )

    fun latest(timestamp:BigInteger): Query = Query(query = """
    {
      swaps(
        orderBy: timestamp
        orderDirection: desc
        where: {
        	timestamp_gt: $timestamp
        }) {
        pool {
            token0 {
                id
                symbol
            }
            token1 {
                id
                symbol
            }
            id
        }
        sender
        recipient
        amount0
        amount1
        id
        timestamp
        transaction {
          gasUsed
          gasPrice
          id
          blockNumber
          timestamp
        }
      }
    }
    """.trimIndent())

    fun swaps(poolId:String, count:Int, direction: Direction): Query = Query(query = """
    {
      swaps(
        orderBy: timestamp
        orderDirection: ${direction.order}
        ${direction.prefix}: $count
        where: {
   	        pool_: {
                id: "$poolId"
            }
        }) {
        pool {
            token0 {
                id
                symbol
            }
            token1 {
                id
                symbol
            }
            id
        }
        sender
        recipient
        amount0
        amount1
        id
        timestamp
        transaction {
          gasUsed
          gasPrice
          id
          blockNumber
          timestamp
        }
      }
    }
    """.trimIndent())

}