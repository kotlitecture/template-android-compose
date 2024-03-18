package core.ui.navigation

data class NavigationData<D>(
    val destination: NavigationDestination<D>?,
    val strategy: NavigationStrategy,
    val data: D?,
    val uid: Long = System.nanoTime()
)