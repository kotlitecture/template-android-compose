package app.userflow.webtonative.command

import core.data.misc.utils.IdUtils
import kotlinx.serialization.Serializable

@Serializable
data class WebCommand(
    val id: String = IdUtils.autoId(),
    val action: String,
    val args: Map<String, String>
)