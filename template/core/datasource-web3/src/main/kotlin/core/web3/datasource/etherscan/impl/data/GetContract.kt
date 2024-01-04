package core.web3.datasource.etherscan.impl.data

import com.google.gson.annotations.SerializedName

class GetContract {

    data class Response(
        @SerializedName("result")
        val items: List<Item?> = emptyList()
    ) {
        data class Item(
            @SerializedName("SourceCode")
            val sourceCode: String? = null,
            @SerializedName("ConstructorArguments")
            val arguments: String? = null,
            @SerializedName("ABI")
            val abi: String? = null,
            @SerializedName("ContractName")
            val name: String? = null,
        )
    }

}