package core.web3.model

import com.google.gson.annotations.SerializedName

data class IpfsFile(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Size")
    val size: Long,
//    @SerializedName("Type")
//    val type: Int,
//    @SerializedName("Hash")
//    val hash:String,
)