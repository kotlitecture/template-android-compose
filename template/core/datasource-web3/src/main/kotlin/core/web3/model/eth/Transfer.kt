package core.web3.model.eth

data class Transfer(
    val transactionHash: String,
    val contractAddress:String,
    val events: List<TransferEvent>
)