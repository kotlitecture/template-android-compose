package core.web3.interactor.eth.contract.impl

import core.web3.IWeb3Context
import core.web3.interactor.eth.contract.IOpenSeaProtocol
import core.web3.model.Wallet
import org.web3j.abi.datatypes.Address
import org.web3j.protocol.Web3j
import java.math.BigInteger

internal class OpenSeaProtocol(
    contractAddress: String,
    context: IWeb3Context,
    wallet: Wallet,
    web3: Web3j,
) : BaseContract(
    contractAddress,
    context,
    wallet,
    web3
), IOpenSeaProtocol {

    override suspend fun getCounter(): BigInteger {
        return callNumberMethod(
            method = "getCounter",
            inputs = listOf(Address(wallet.address))
        ) ?: BigInteger.ZERO
    }

}