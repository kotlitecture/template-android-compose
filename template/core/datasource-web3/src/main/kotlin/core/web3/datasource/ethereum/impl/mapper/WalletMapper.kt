package core.web3.datasource.ethereum.impl.mapper

import core.web3.model.Wallet
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair

object WalletMapper {

    fun toCredentials(from: Wallet): Credentials {
        return Credentials.create(from.privateKey)
    }

    fun toECKeyPair(from: Wallet): ECKeyPair {
        return toCredentials(from).ecKeyPair
    }

}