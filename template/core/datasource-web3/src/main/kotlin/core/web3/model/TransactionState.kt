package core.web3.model

sealed class TransactionState(val completed: Boolean, val message: String?) {
    class Error(message: String) : TransactionState(true, message)
    class NotStarted : TransactionState(false, null)
    class Pending : TransactionState(false, null)
    class Success : TransactionState(true, null)
    class Unknown : TransactionState(false, null)
}