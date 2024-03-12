package core.ui.state

sealed class DataState {
    data class Loading(
        val id: String
    ) : DataState()

    data class Loaded(
        val id: String
    ) : DataState()

    data class Error(
        val id: String,
        val th: Throwable
    ) : DataState()
}