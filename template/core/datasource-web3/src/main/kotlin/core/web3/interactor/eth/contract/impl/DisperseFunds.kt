package core.web3.interactor.eth.contract.impl

import core.web3.IWeb3Context
import core.web3.command.CommandException
import core.web3.interactor.eth.contract.IDisperseFunds
import core.web3.datasource.ethereum.impl.EthereumTransaction
import core.web3.extensions.weiFromEth
import core.web3.model.AssetValue
import core.web3.model.ITransaction
import core.web3.model.Wallet
import core.web3.model.eth.UserTx
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import java.math.BigInteger

internal class DisperseFunds(
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    "0xd152f549545093347a162dce210e7293f1452150",
    context,
    wallet,
    web3
), IDisperseFunds {

    override suspend fun disperse(to: List<AssetValue>): ITransaction {
        if (to.isEmpty()) throw CommandException("no addresses to transfer ETH")
        val addresses = to.map { it.address }
        val amounts = to.map { it.value.weiFromEth() }
        val toAddress: String
        val input: String?
        when (amounts.size) {
            1 -> {
                input = null
                toAddress = addresses.first()
            }

            else -> {
                val addressList = DynamicArray(Address::class.java, addresses.map { Address(it) })
                val amountList = DynamicArray(Uint256::class.java, amounts.map { Uint256(it) })
                val function =
                    Function("disperseEther", listOf(addressList, amountList), emptyList())
                input = FunctionEncoder.encode(function)
                toAddress = contractAddress
            }
        }
        val payable = amounts.sumOf { it }
        val userTx = UserTx(
            to = toAddress,
            from = wallet,
            input = input,
            value = payable,
            gasPrice = BigInteger.ZERO,
            gasLimit = BigInteger.ZERO
        )
        return EthereumTransaction(
            userTx = userTx,
            context = context,
            name = wallet.name ?: wallet.address
        )
    }

}