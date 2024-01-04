package core.web3.command.eth.quicktask

import core.web3.IWeb3Context
import core.web3.command.AbstractCommand
import core.web3.command.eth.quicktask.provider.InputValuesProvider
import core.web3.command.eth.quicktask.provider.UserInputValuesProvider
import core.web3.datasource.ethereum.impl.EthereumTransaction
import core.web3.model.ITransaction
import core.web3.model.Wallet
import core.web3.model.eth.Mint
import core.web3.model.eth.UserTx
import org.tinylog.Logger
import org.web3j.abi.FunctionEncoder

/**
 * Copies already completed transaction with own parameters.
 * Kind of 'quick task'.
 *
 * @return transaction executor.
 */
class QuickTaskMint(
    private val mint: Mint,
    private val wallet: Wallet
) : AbstractCommand<ITransaction>() {

    override suspend fun doExecute(context: IWeb3Context): ITransaction {
        val ethereumSource = context.getEthereumSource()
        val input: String =
            if (!mint.isMintable()) {
                Logger.debug("QT of not verified contract :: {}", mint.input)
                val regex = Regex("0{24}(.{40})")
                val matches = regex.findAll(mint.input)
                    .toList()
                    .mapNotNull { it.groups[1] }
                    .filter {
                        val address = "0x${it.value}"
                        if (address == mint.to) return@filter false
                        if (address == Wallet.NULL_ADDRESS) return@filter false
                        if (address == mint.collectionAddress) return@filter false
                        ethereumSource.isWallet(address)
                    }
                if (matches.isNotEmpty()) {
                    val sb = StringBuilder()
                    var idx = 0
                    matches.forEach { match ->
                        val range = match.range
                        sb.append(mint.input.substring(idx, range.first))
                        sb.append(wallet.address.removePrefix("0x"))
                        idx = range.last + 1
                    }
                    sb.append(mint.input.substring(idx))
                    sb.toString()
                } else {
                    mint.input
                }
            } else {
                val input = mint.input
                val functionId = mint.functionId!!
                val functionName = mint.functionName!!
                val functionInputs = mint.functionInputs
                val functionOutputs = mint.functionOutputs
                val inputTypes = functionInputs.map { it.second }
                val outputTypes = functionOutputs.map { it.second }
                val inputValuesProvider = InputValuesProvider(input, functionId, functionInputs)
                val userValuesProvider = UserInputValuesProvider(wallet, inputValuesProvider)
                val userValues = userValuesProvider.provide().map { it.value }
                val function = FunctionEncoder.makeFunction(
                    functionName,
                    inputTypes,
                    userValues,
                    outputTypes
                )
                Logger.debug("QT :: function={}", function)
                FunctionEncoder.encode(function)
            }
        val userTx = UserTx(
            input = input,
            maxPriorityFeePerGas = mint.maxPriority,
            maxFeePerGas = mint.maxFeePerGas,
            gasLimit = mint.gasLimit,
            gasPrice = mint.gasPrice,
            value = mint.value,
            from = wallet,
            to = mint.to
        )
        return EthereumTransaction(
            userTx = userTx,
            context = context,
            name = mint.collectionName ?: mint.collectionAddress,
            description = mint.functionSignature,
            iconUrl = mint.collectionIcon,
        )
    }

}