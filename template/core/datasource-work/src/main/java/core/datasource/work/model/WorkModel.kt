package core.datasource.work.model

import java.util.Date

data class WorkModel(
    val id: Long = 0,
    val uid: String,
    val type: String,
    val input: String,
    val parentId: String?,
    val name: String? = null,
    val icon: String? = null,
    val output: String? = null,
    val updateDate: Date? = null,
    val createDate: Date = Date(),
    val description: String? = null,
    val stateReason: String? = null,
    val state: WorkState = WorkState.Created
)