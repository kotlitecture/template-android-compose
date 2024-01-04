package core.web3.datasource.blocknative.impl.data

class InitializeEvent : RequestEvent() {
    override val categoryCode: String = "initialize"
    override val eventCode: String = "checkDappId"
}