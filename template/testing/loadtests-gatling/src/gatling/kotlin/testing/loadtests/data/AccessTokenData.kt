package testing.loadtests.data

data class AccessTokenData(
    var accessToken: String = "",
    var refreshToken: String = ""
) {
    companion object {
        const val ID = "AccessTokenData"
    }
}