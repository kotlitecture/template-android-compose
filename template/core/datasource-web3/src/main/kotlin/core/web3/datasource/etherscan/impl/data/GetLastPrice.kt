package core.web3.datasource.etherscan.impl.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

class GetLastPrice {

    data class Response(
        @SerializedName("result")
        val result: Result
    )

    data class Result(
        @SerializedName("ethusd")
        val ethusd: BigDecimal,
        @SerializedName("ethbtc")
        val ethbtc: BigDecimal
    )

}