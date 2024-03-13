package core.ui.navigation

data class NavigationData<D>(
    val destination: NavigationDestination<D>? = null,
    val data: D? = null,
    val uid: Long = System.nanoTime()
)