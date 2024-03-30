package app.ui.navigation

/**
 * Represents a navigation bar page item.
 *
 * @param id The unique id associated with the page item.
 * @param getIcon The icon of the page item.
 * @param getLabel The label of the page item.
 * @param enabled Indicates whether the page item is enabled or disabled. Default is true.
 * @param alwaysShowLabel Indicates whether to always show the label, regardless of its presence. Default is true if label is not null.
 * @param onClick The callback to be invoked when the page item is clicked.
 */
data class NavigationBarPage(
    val id: String,
    val enabled: Boolean = true,
    val alwaysShowLabel: Boolean = true,
    val getLabel: () -> String?,
    val getIcon: () -> Any,
    val onClick: () -> Unit
)