package core.web3.datasource.blocknative.impl.data

import com.google.gson.annotations.SerializedName

class AccountEvent : RequestEvent() {
    override val categoryCode: String = "accountAddress"
    override val eventCode: String = "watch"

    @get:SerializedName("account")
    var account: Account? = null

    data class Account(
        @get:SerializedName("address")
        var address: String
    )
}