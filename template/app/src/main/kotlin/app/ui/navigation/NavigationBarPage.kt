package app.ui.navigation

data class NavigationBarPage<D>(
    val model: D,
    val icon: Any,
    val label: String?,
    val enabled: Boolean = true,
    val alwaysShowLabel: Boolean = label != null,
    val onClick: () -> Unit
)