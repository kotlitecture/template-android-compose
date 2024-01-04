package core.web3.command.eth.collection

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.model.IpfsFile
import org.tinylog.Logger
import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min

class GetRarestTokens(
    private val withMaxCount: Int = 60,
    private val withCollectionAddress: String,
    private val withExperimentalApi: Boolean = true,
    private val withMetadataSizeChangeFactor: Float = 0.05f
) : AbstractCommand<List<BigInteger>>() {

    override suspend fun doExecute(context: IWeb3Context): List<BigInteger> {
        val interactor = context.getEthereumInteractor()
        val ipfsSource = context.getIpfsSource()
        val wallet = context.getEthWallet()

        // get contract
        val contract = interactor.contractErc721(wallet, withCollectionAddress)

        // get any token uri
        val tokenUri = contract.tokenURI(BigInteger.ONE) ?: return emptyList()

        // get catalog id
        val catalogId = ipfsSource.getCatalogId(tokenUri) ?: return emptyList()

        // get files in catalog
        val getFiles = when {
            withExperimentalApi -> runCatching { ipfsSource.getFilesExperimental(catalogId) }
                .getOrNull()
                ?.takeIf { it.size >= withMaxCount }
                ?: ipfsSource.getFiles(catalogId)

            else -> ipfsSource.getFiles(catalogId)
        }
        val files = getFiles.takeIf { it.isNotEmpty() } ?: return emptyList()
        Logger.debug("found files :: {}", files.size)

        // find rarest
        val rarest = mutableListOf<IpfsFile>()
        files.take(withMaxCount).forEach { file ->
            val newSize = file.size
            val prevFile = rarest.lastOrNull()
            val prevSize = prevFile?.size ?: newSize
            val maxSize = max(prevSize, newSize).toFloat()
            val minSize = min(prevSize, newSize).toFloat()
            val changeFactor = 1f - minSize / maxSize
            Logger.debug(
                "change factor : ({}, {}) - ({}, {}) -> {}",
                prevFile?.name,
                prevFile?.size,
                file.name,
                file.size,
                changeFactor
            )
            if (changeFactor <= withMetadataSizeChangeFactor) {
                rarest.add(file)
            }
        }

        val found = rarest.mapNotNull { it.name.toBigIntegerOrNull() }

        Logger.debug("found :: {} -> {}", found.size, found)

        return found.takeIf { it.size < withMaxCount } ?: emptyList()
    }

}