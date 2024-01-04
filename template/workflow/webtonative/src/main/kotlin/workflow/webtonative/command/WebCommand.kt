package workflow.webtonative.command

import core.essentials.misc.utils.IdUtils

data class WebCommand(
    val id: String = IdUtils.autoId(),
    val action: String,
    val args: Map<String, Any>
)