package core.web3.datasource.blocknative

import core.web3.datasource.blocknative.impl.data.TransactionEvent
import kotlinx.coroutines.flow.Flow

interface IBlocknativeSource {

    suspend fun getChanges(address: String): Flow<TransactionEvent>

}