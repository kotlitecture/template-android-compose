package core.ui

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Immutable
data class AppContext(
    val snackbarHostSate: SnackbarHostState,
    val navController: NavHostController,
    val scope: CoroutineScope,
    val context: Context
)

@Composable
fun rememberAppContext(): AppContext {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    return remember(context) {
        AppContext(
            snackbarHostSate = SnackbarHostState(),
            navController = navController,
            context = context,
            scope = scope
        )
    }
}