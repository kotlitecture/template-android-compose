package core.web3.interactor.eth.contract

/**
 * https://docs.uniswap.org/contracts/v2/reference/smart-contracts/pair
 * https://docs.uniswap.org/contracts/v2/reference/smart-contracts/Pair-ERC-20
 * */
interface IUniswapPair : IContract {

    suspend fun token0(): IErc20

    suspend fun token1(): IErc20

    suspend fun tokenNotWeth(): IErc20

}