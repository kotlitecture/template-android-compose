package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.web3.extensions.weiFromEth
import core.web3.extensions.weiToEth
import core.web3.flow.pool.PoolFlowContext

class CheckBuyCommission : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val wallet = context.wallet
        var commission = 0
        val step = 10
        val api = context.web3.getEthereumInteractor()
        val router = api.contractUniswapRouter(context.platform, wallet)!!
        val balance = context.web3.getEthereumSource().getBalance(wallet.address).weiToEth()
        if (balance <= context.buyAmountInEth) return
        while (commission != 100) {
            val transaction = router.buyTokens(
                amountIn = context.buyAmountInEth.weiFromEth(),
                slippage = 1f - (commission / 100f),
                tokenAddress = context.pool.token0.id
            )
            val maxFee = minOf(context.transactionFeeInEth, balance.minus(context.buyAmountInEth))
            val executable = transaction.isExecutable(maxFee)
            if (executable) {
                context.contractIsExecutable = true
                context.poolBuySlippage = commission
                return
            }
            commission += step
        }
    }

}