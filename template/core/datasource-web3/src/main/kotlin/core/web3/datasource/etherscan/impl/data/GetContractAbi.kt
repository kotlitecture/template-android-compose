package core.web3.datasource.etherscan.impl.data

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class GetContractAbi {

    data class Response(
        @SerializedName("result")
        val result: String
    ) {

        data class FunctionData(
            @SerializedName("type") val type: Type?,
            @SerializedName("name") val name: String?,
            @SerializedName("stateMutability") val stateMutability: String?,
            @SerializedName("inputs") val inputs: List<ParameterData> = emptyList(),
            @SerializedName("outputs") val outputs: List<ParameterData> = emptyList()
        ) {
            fun isPayable(): Boolean = stateMutability == "payable"
        }

        data class ParameterData(
            @SerializedName("name")
            val name: String?,
            @SerializedName("type")
            val type: String?
        )

        enum class Type {
            @SerializedName("function")
            Function,

            @SerializedName("constructor")
            Constructor,

            @SerializedName("error")
            Error,

            @SerializedName("event")
            Event
        }

        class FunctionDataList : TypeToken<List<FunctionData>>()

    }

}