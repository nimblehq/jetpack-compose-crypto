package co.nimblehq.compose.crypto.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.detail.DetailViewModel
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen

@Composable
fun CryptoNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = CryptoScreen.HOME.name
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = CryptoScreen.HOME.name) {
            HomeScreen(
                onMyCoinsItemClick = {
                    navController.navigate(route = CryptoScreen.COIN_INFORMATION.name)
                }
            )
        }
        composable(
            route = "${CryptoScreen.COIN_INFORMATION.name}/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val detailViewModel: DetailViewModel by componentActivity.provideViewModels()
            DetailScreen(
                navController = navController,
                viewModel = detailViewModel,
                id = it.arguments?.getString("id").orEmpty()
            )
        }
    }
}
