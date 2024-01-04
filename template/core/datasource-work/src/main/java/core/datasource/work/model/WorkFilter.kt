package core.datasource.work.model

data class WorkFilter(
    val stateIn: List<WorkState>,
    val parentId: String? = null,
    val includeChildren: Boolean = false,
    val sortBy: String = SORT_BY_CREATE_DATE_DESC
) {
    companion object {
        const val SORT_BY_CREATE_DATE_ASC = "create_date"
        const val SORT_BY_CREATE_DATE_DESC = "create_date DESC"
        const val SORT_BY_UPDATE_DATE_DESC = "update_date DESC"

        fun filterActive(): WorkFilter = WorkFilter(
            stateIn = WorkState.Active
        )

        fun filterHistory(): WorkFilter = WorkFilter(
            stateIn = WorkState.History
        )

        fun filterPending(): WorkFilter = WorkFilter(
            stateIn = WorkState.Pending
        )
    }
}