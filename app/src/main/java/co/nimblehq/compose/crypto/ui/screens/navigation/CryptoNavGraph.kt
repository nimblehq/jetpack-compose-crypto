package co.nimblehq.compose.crypto.ui.screens.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.extension.provideViewModels
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.detail.DetailViewModel
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen
import co.nimblehq.compose.crypto.ui.screens.home.HomeViewModel

@Composable
fun CryptoNavGraph(
    componentActivity: ComponentActivity,
    navController: NavHostController = rememberNavController(),
    startDestination: String = CryptoScreen.HOME.name
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = CryptoScreen.HOME.name) {
            val homeViewModel: HomeViewModel by componentActivity.provideViewModels()
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel,
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
