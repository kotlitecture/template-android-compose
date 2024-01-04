package core.web3.interactor.eth.contract

/**
 * https://docs.uniswap.org/contracts/v2/reference/smart-contracts/factory
 * */
interface IUniswapFactory : IContract {

    suspend fun getPair(tokenA: String, tokenB: String): IUniswapPair

}