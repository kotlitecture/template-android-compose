package core.web3.datasource.uniswap.v3

import com.google.gson.annotations.SerializedName
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.Query

object GetPool {

    data class Response(
        @SerializedName("data")
        val data: Pools
    )

    data class Pools(
        @SerializedName("pool")
        val pool: Pool
    )

    fun byAddress(address: String): Query = Query(query = """
        {
          pool(id: "$address") {
            token0 {
              symbol
              id
              decimals
            }
            token1 {
              symbol
              id
              decimals
            }
            id
            createdAtTimestamp
            token0Price
            token1Price
            txCount
            totalValueLockedETH
            totalValueLockedUSD
          }
        }
    """.trimIndent())

}