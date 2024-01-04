package core.web3.datasource.uniswap.v3

import com.google.gson.annotations.SerializedName
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.Query
import java.math.BigInteger

object GetPools {

    data class Response(
        @SerializedName("data")
        val data: Pools
    )

    data class Pools(
        @SerializedName("pools")
        val pools: List<Pool>
    )

    fun latest(timestamp:BigInteger): Query = Query(query = """
        {
        pools(
        orderBy: createdAtTimestamp, 
        orderDirection: desc,
        where: {
        createdAtTimestamp_gt: $timestamp
        }) {
        id
        token0 {
        id
        symbol
        }
        token1 {
        id
        symbol
        }
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