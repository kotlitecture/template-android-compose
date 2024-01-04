package core.web3.datasource.alchemy.impl.data

import com.google.gson.annotations.SerializedName

class GetTokenBalances {

    data class Request(
        @Transient
        val address: String,
        @SerializedName("id")
        val id: String = "1",
        @SerializedName("jsonrpc")
        val jsonrpc: String = "2.0",
        @SerializedName("method")
        val method: String = "alchemy_getTokenBalances",
        @SerializedName("params")
        val params: List<String> = listOf(address, "erc20")
    )

    data class Response(
        @SerializedName("result")
        val result: Result
    )

    data class Result(
        @SerializedName("tokenBalances")
        val tokenBalances: List<TokenBalance>
    )

    data class TokenBalance(
        @SerializedName("contractAddress")
        val tokenAddress: String,
        @SerializedName("tokenBalance")
        val tokenBalance: String
    )

}