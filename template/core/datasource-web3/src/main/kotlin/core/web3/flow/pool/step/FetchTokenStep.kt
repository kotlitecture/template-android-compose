package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.web3.flow.pool.PoolFlowContext
import core.web3.model.Platform

class FetchTokenStep : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val api = context.web3
        val wallet = context.wallet
        val address = context.tokenAddress
        val platforms = listOf(Platform.UniSwapV2, Platform.UniSwapV3)
        val interactor = api.getEthereumInteractor()
        for (platform in platforms) {
            val router = interactor.contractUniswapRouter(platform, wallet)
            val poolAddress = router?.getFactory()?.getPair(address, router.weth())?.contractAddress
            if (poolAddress != null) {
                context.platform = platform
                context.pool = api.getUniswapSource(platform)?.getPool(poolAddress)!!
                return
            }
        }
    }

}