package core.web3.model

data class Wallet(
    val address: String,
    val privateKey: String,
    val name: String? = null,
    val blockchain: Blockchain
) {
    companion object {
        const val NULL_ADDRESS = "0x0000000000000000000000000000000000000000"
    }
}