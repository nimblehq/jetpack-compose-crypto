package co.nimblehq.compose.crypto.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.nimblehq.compose.crypto.CryptoAppState
import co.nimblehq.compose.crypto.extension.collectAsEffect

@Composable
fun ComposeCryptoApp(
    navController: NavHostController = rememberNavController(),
    cryptoAppState: CryptoAppState
) {
    var onInternetRestore: () -> Unit = {}
    val context = LocalContext.current

    cryptoAppState.isNetworkConnected.collectAsEffect { isNetworkConnected ->
        if (isNetworkConnected == false) {
            val destination = AppDestination.NoNetwork

            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute == AppDestination.NoNetwork.route) {
                navController.popBackStack()
            }

            navController.navigate(destination)
        }
    }

    cryptoAppState.isNetworkError.collectAsEffect { error ->
        Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
    }

    AppNavigation(
        navController = navController,
        onCallBackChange = {
            onInternetRestore = it
        },
        onInternetRestore = {
            onInternetRestore.invoke()
        },
    )
}
