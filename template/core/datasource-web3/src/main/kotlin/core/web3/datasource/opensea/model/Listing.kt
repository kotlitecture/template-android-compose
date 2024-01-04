package core.web3.datasource.opensea.model

import core.web3.model.Blockchain

data class Listing(
    val hash: String,
    val chain: Blockchain,
    val protocolAddress: String,
)