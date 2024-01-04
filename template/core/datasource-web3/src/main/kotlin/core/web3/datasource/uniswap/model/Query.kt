package core.web3.datasource.uniswap.model

import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("query")
    val query: String
)
