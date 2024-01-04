package core.web3.datasource.alchemy.impl.data

import com.google.gson.annotations.SerializedName

class GetAsset {

    data class Request(
        @Transient
        val address: String,
        @SerializedName("id")
        val id: String = "1",
        @SerializedName("jsonrpc")
        val jsonrpc: String = "2.0",
        @SerializedName("method")
        val method: String = "alchemy_getTokenMetadata",
        @SerializedName("params")
        val params: List<String> = listOf(address)
    )

    data class Response(
        @SerializedName("result")
        val result: Result
    )

    data class Result(
        @SerializedName("decimals")
        val decimals: Int,
        @SerializedName("logo")
        val logo: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("symbol")
        val symbol: String?,
    )

}