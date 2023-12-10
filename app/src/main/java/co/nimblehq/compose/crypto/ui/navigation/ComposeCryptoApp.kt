package co.nimblehq.compose.crypto.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val context = LocalContext.current

    val isNetworkConnected by cryptoAppState.isNetworkConnected.collectAsState()

    LaunchedEffect(isNetworkConnected) {
        if (isNetworkConnected == false) {
            val destination = AppDestination.NoNetwork

            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute != AppDestination.NoNetwork.route) {
                navController.navigate(destination)
            }
        }
    }

    cryptoAppState.networkError.collectAsEffect { error ->
        Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
    }

    AppNavigation(navController = navController)
}
