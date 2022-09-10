package co.nimblehq.compose.crypto.ui.screens.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.extension.provideViewModels
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
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
        composable(CryptoScreen.HOME.name) {
            val homeViewModel: HomeViewModel by componentActivity.provideViewModels()
            HomeScreen(
                viewModel = homeViewModel,
                onMyCoinsItemClick = {
                    navController.navigate(route = CryptoScreen.COIN_INFORMATION.name)
                }
            )
        }
        composable(CryptoScreen.COIN_INFORMATION.name) {
            DetailScreen()
        }
    }
}
