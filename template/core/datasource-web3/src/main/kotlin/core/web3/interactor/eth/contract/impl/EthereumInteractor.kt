package core.web3.interactor.eth.contract.impl

import core.web3.IWeb3Context
import core.web3.interactor.eth.IEthereumInteractor
import core.web3.interactor.eth.contract.IDisperseFunds
import core.web3.interactor.eth.contract.IErc20
import core.web3.interactor.eth.contract.IErc721
import core.web3.interactor.eth.contract.IOpenSeaProtocol
import core.web3.interactor.eth.contract.IUniswapPair
import core.web3.interactor.eth.contract.IUniswapRouter
import core.web3.model.Platform
import core.web3.model.Wallet
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class EthereumInteractor(
    private val context: IWeb3Context,
    private val nodeUrl: String
) : IEthereumInteractor {

    private val web3 by lazy { Web3j.build(HttpService(nodeUrl, context.getHttpSource().okhttp)) }

    override fun contractDisperseFunds(wallet: Wallet): IDisperseFunds {
        return DisperseFunds(
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

    override fun contractUniswapV2Router(wallet: Wallet): IUniswapRouter {
        return UniswapV2Router(
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

    override fun contractUniswapV3Router(wallet: Wallet): IUniswapRouter {
        return UniswapV2Router(
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

    override fun contractUniswapRouter(platform: Platform, wallet: Wallet): IUniswapRouter? {
        return when (platform) {
            Platform.UniSwapV2 -> contractUniswapV2Router(wallet)
            Platform.UniSwapV3 -> contractUniswapV3Router(wallet)
            else -> null
        }
    }

    override fun contractUniswapPair(wallet: Wallet, address: String): IUniswapPair {
        return UniswapPair(
            contractAddress = address,
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

    override fun contractErc721(wallet: Wallet, address: String): IErc721 {
        return Erc721(
            contractAddress = address,
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

    override fun contractErc20(wallet: Wallet, address: String): IErc20 {
        return Erc20(
            contractAddress = address,
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

    override fun contractOpenSeaProtocol(wallet: Wallet, address: String): IOpenSeaProtocol {
        return OpenSeaProtocol(
            contractAddress = address,
            context = context,
            wallet = wallet,
            web3 = web3
        )
    }

}