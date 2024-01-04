@file:Suppress("UNCHECKED_CAST")

package core.web3.command.eth.contract

import core.essentials.misc.extensions.withJobAsync
import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.AssetContract
import core.web3.model.Collection
import core.web3.model.eth.Block
import core.web3.model.eth.Mint
import core.web3.model.eth.TransferEvent
import kotlinx.coroutines.awaitAll

/**
 * Gets mint meta by transaction hash.
 */
class GetMintByHash(
    private val hash: String
) : AbstractCommand<Mint>() {

    override suspend fun doExecute(context: IWeb3Context): Mint {
        val wallet = context.getEthWallet()
        val openSeaSource = context.getOpenSeaSource()
        val ethereumSource = context.getEthereumSource()
        val interactor = context.getEthereumInteractor()
        val tx = ethereumSource.getTx(hash)!!
        val abi = GetContractAbiByTx(tx).executeOptional(context)
        val contract = interactor.contractErc721(wallet, abi?.address ?: tx.to)
        val attrs = listOf(
            withJobAsync { ethereumSource.getBlock(tx.blockNumber) },
            withJobAsync { contract.getAssetContract() },
            withJobAsync { ethereumSource.getTokenTransfer(hash)?.events },
            withJobAsync { openSeaSource.getByAddress(tx.to) }
        ).awaitAll().map { it.getOrNull() }
        val block = attrs.getOrNull(0) as Block
        val function = abi?.getFunctionByInput(tx.input)
        val metadata = attrs.getOrNull(1) as AssetContract
        val transfers = attrs.getOrNull(2) as List<TransferEvent>
        val collection = attrs.getOrNull(3) as? Collection
        return Mint(
            maxSupply = metadata.maxSupply,
            collectionName = collection?.name ?: metadata.name,
            collectionAddress = collection?.address ?: tx.to,
            collectionIcon = collection?.imageUrl,
            tokens = transfers.map { it.id }.sorted(),
            to = tx.to,
            from = tx.from,
            txHash = tx.hash,
            input = tx.input,
            value = tx.value,
            gasUsed = tx.gasUsed,
            gasLimit = tx.gasLimit,
            gasPrice = tx.gasPrice,
            blockNumber = block.number,
            blockTimestamp = block.timestamp,
            maxPriority = tx.maxPriority,
            maxFeePerGas = tx.maxFeePerGas,
            functionId = function?.id,
            functionName = function?.name,
            functionSignature = function?.signature,
            functionInputs = function?.inputs
                ?.map { it.name.orEmpty() to it.type.orEmpty() }
                ?: emptyList(),
            functionOutputs = function?.outputs
                ?.map { it.name.orEmpty() to it.type.orEmpty() }
                ?: emptyList()
        )
    }

}