package core.web3.datasource.blocknative.impl.data

import com.google.gson.annotations.SerializedName
import java.util.Date

abstract class RequestEvent {

    @get:SerializedName("categoryCode")
    abstract val categoryCode: String

    @get:SerializedName("eventCode")
    abstract val eventCode: String

    @get:SerializedName("timeStamp")
    val timeStamp: Date = Date()

    @get:SerializedName("dappId")
    var dappId: String? = null

    @get:SerializedName("appName")
    var appName: String? = null

    @get:SerializedName("appVersion")
    var appVersion: String? = null

    @get:SerializedName("version")
    var version: String? = "0.0.1"

    @get:SerializedName("blockchain")
    var blockchain: Blockchain = Blockchain()

    data class Blockchain(
        @get:SerializedName("system")
        val system: String = "ethereum",
        @get:SerializedName("network")
        val network: String = "main"
    )
}