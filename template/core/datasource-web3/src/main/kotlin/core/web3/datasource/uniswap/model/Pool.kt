package core.web3.datasource.uniswap.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.BigInteger

data class Pool(
    val id: String,
    val token0: Token,
    val token1: Token,
    val createdAtTimestamp: BigInteger?,
    val token0Price: BigDecimal?,
    val token1Price: BigDecimal?,
    val txCount: BigInteger?,
    @SerializedName("totalValueLockedETH", alternate = ["reserveETH"])
    val totalValueLockedETH: BigDecimal?,
    @SerializedName("totalValueLockedUSD", alternate = ["reserveUSD"])
    val totalValueLockedUSD: BigDecimal?
) {
    fun isWethPair(): Boolean = token0.isWeth() || token1.isWeth()

    fun normalize(): Pool {
        if (token0.isWeth()) {
            return copy(
                token0 = token1,
                token1 = token0,
                token0Price = token1Price,
                token1Price = token0Price
            )
        }
        return this
    }
}