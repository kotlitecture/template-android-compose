package app.userflow.loading

sealed class LoadingState(private val actionId: String?) {
    data class Loading(
        val id: String? = null
    ) : LoadingState(id)

    data class Loaded(
        val id: String? = null
    ) : LoadingState(id)

    data class Error(
        val id: String? = null,
        val code: String? = null,
        val message: String? = null,
        val th: Throwable
    ) : LoadingState(id)
}