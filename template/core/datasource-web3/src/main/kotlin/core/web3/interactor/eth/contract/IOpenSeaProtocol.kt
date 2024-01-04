package core.web3.interactor.eth.contract

import java.math.BigInteger

interface IOpenSeaProtocol : IContract {

    suspend fun getCounter(): BigInteger

}