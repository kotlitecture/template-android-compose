package core.web3.interactor.eth

import core.web3.interactor.IChainInteractor
import core.web3.interactor.eth.contract.IDisperseFunds
import core.web3.interactor.eth.contract.IErc20
import core.web3.interactor.eth.contract.IErc721
import core.web3.interactor.eth.contract.IOpenSeaProtocol
import core.web3.interactor.eth.contract.IUniswapPair
import core.web3.interactor.eth.contract.IUniswapRouter
import core.web3.model.Platform
import core.web3.model.Wallet

interface IEthereumInteractor : IChainInteractor {

    fun contractUniswapV2Router(wallet: Wallet): IUniswapRouter

    fun contractUniswapV3Router(wallet: Wallet): IUniswapRouter

    fun contractUniswapRouter(platform: Platform, wallet: Wallet): IUniswapRouter?

    fun contractUniswapPair(wallet: Wallet, address: String): IUniswapPair

    fun contractDisperseFunds(wallet: Wallet): IDisperseFunds

    fun contractErc721(wallet: Wallet, address: String): IErc721

    fun contractErc20(wallet: Wallet, address: String): IErc20

    fun contractOpenSeaProtocol(wallet: Wallet, address: String): IOpenSeaProtocol

}