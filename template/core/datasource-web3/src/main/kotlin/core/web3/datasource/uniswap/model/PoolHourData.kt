package core.web3.datasource.uniswap.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.BigInteger

data class PoolHourData(
    @SerializedName("id")
    val id: String,
    @SerializedName("txCount")
    val txCount: BigInteger,
    @SerializedName("liquidity")
    val liquidity: BigInteger,
    @SerializedName("open")
    val open: BigDecimal,
    @SerializedName("close")
    val close: BigDecimal,
    @SerializedName("high")
    val high: BigDecimal,
    @SerializedName("low")
    val low: BigDecimal,
    @SerializedName("token0Price")
    val token0Price: BigDecimal,
    @SerializedName("token1Price")
    val token1Price: BigDecimal,
    @SerializedName("volumeToken0")
    val volumeToken0: BigDecimal,
    @SerializedName("volumeToken1")
    val volumeToken1: BigDecimal,
    @SerializedName("volumeUSD")
    val volumeUSD: BigDecimal,
    @SerializedName("periodStartUnix")
    val periodStartUnix: BigInteger
)
