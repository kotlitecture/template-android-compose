package core.web3.command

import core.web3.IWeb3Context

interface ICommand<R> {

    suspend fun execute(context: IWeb3Context): R

    suspend fun executeOptional(context: IWeb3Context): R?

}