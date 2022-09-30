package co.nimblehq.compose.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Home.destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = AppDestination.Home.route
        ) {
            HomeScreen(
                navigator = { destination -> navController.navigate(destination) }
            )
        }

        composable(
            // FIXME dummy CoinDetail initialization for routing
            route = AppDestination.CoinDetail("").route,
            arguments = listOf(navArgument("coinId") {
                type = NavType.StringType
            })
        ) {
            DetailScreen(
                navigator = { destination -> navController.navigate(destination) },
                coinId = it.arguments?.getString("coinId").orEmpty()
            )
        }
    }
}

private fun NavHostController.navigate(destination: AppDestination) {
    when (destination) {
        is AppDestination.Up -> popBackStack()
        else -> navigate(route = destination.destination)
    }
}
