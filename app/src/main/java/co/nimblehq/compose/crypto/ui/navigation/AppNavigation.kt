package co.nimblehq.compose.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = AppDestination.Home.route
        ) {
            HomeScreen(
                navigator = { destination -> navController.navigate(route = destination.route) }
            )
        }
        composable(
            route = AppDestination.CoinDetail.route
        ) {
            DetailScreen()
        }
    }
}
