package core.ui.navigation

/**
 * Data class representing navigation data containing information about the destination, navigation strategy,
 * data to be passed, and a unique identifier.
 *
 * @param destination The destination to navigate to.
 * @param strategy The navigation strategy to be used.
 * @param data The data to be passed during navigation.
 * @param uid A unique identifier for the navigation data. Defaults to the current system nano time.
 */
data class NavigationData<D>(
    val destination: NavigationDestination<D>?,
    val strategy: NavigationStrategy,
    val data: D?,
    val uid: Long = System.nanoTime()
)