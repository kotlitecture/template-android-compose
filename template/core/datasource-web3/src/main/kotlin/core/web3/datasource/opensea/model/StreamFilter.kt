package core.web3.datasource.opensea.model

import core.web3.model.Blockchain

data class StreamFilter(
    val slug: String = COLLECTION_ANY,
    val events: List<StreamType> = emptyList(),
    val chains: List<Blockchain> = emptyList()
) {
    companion object {
        const val COLLECTION_ANY = "*"
    }
}