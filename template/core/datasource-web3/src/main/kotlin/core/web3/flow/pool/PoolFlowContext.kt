package core.web3.flow.pool

import core.essentials.flow.FlowContext
import core.web3.IWeb3Context
import core.web3.datasource.uniswap.model.Pool
import core.web3.datasource.uniswap.model.Swap
import core.web3.model.Platform
import core.web3.model.Wallet
import java.math.BigDecimal
import java.math.BigInteger
import java.util.Date

data class PoolFlowContext(
    val tokenAddress: String,
    val web3: IWeb3Context,
    val wallet: Wallet,

    val buyAmountInEth: BigDecimal = BigDecimal("0.01"),
    val transactionFeeInEth: BigDecimal = BigDecimal("0.02")
) : FlowContext() {

    lateinit var platform: Platform
    lateinit var pool: Pool

    var contractIsVerified: Boolean = false
    var contractIsIncorrect: Boolean = false
    var contractIsRenounced: Boolean = false
    var contractIsExecutable: Boolean = false
    var contractIsSuspicious: Boolean = false
    var contractSourceCode: String? = null

    var poolLiquidity: BigDecimal = BigDecimal.ZERO
    var poolTransactionsCount: BigInteger = BigInteger.ZERO
    var poolEarliestTransactions: List<Swap> = emptyList()
    var poolLatestTransactions: List<Swap> = emptyList()
    var poolLastTransactionDate: Date? = null
    var poolBuySlippage: Int? = null

    var aimBotBuyPosition: Int = 0
    var aimBotSalePosition: Int = 0
    var aimBotProfit: BigDecimal? = null

    var telegramUrl: String? = null
    var twitterUrl: String? = null
    var discordUrl: String? = null
    var siteUrl: String? = null

    var score:Int = 0

    override fun toString(): String {
        return "PoolFlowContext(contractIsVerified=$contractIsVerified, contractIsIncorrect=$contractIsIncorrect, contractIsRenounced=$contractIsRenounced, contractIsExecutable=$contractIsExecutable, contractIsSuspicious=$contractIsSuspicious, contractSourceCode=${contractSourceCode != null}, poolLiquidity=$poolLiquidity, poolTransactionsCount=$poolTransactionsCount, poolLastTransactionDate=$poolLastTransactionDate, poolBuySlippage=$poolBuySlippage, telegramUrl=$telegramUrl, twitterUrl=$twitterUrl, discordUrl=$discordUrl, siteUrl=$siteUrl)"
    }

}