package core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class Args<D>(
    val data: D
)