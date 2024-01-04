package core.web3.datasource.opensea.impl.data

import com.google.gson.annotations.SerializedName
import core.web3.model.CollectionStat

object GetCollectionStat {

    data class Response(
        @SerializedName("stats")
        val stats: CollectionStat
    )

}