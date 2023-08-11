package co.nimblehq.compose.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.common.AppDialogPopUp
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = AppDestination.Home.destination,
    onCallBackChange: (() -> Unit) -> Unit,
    globalDialogCallback: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestination.Home) {
            HomeScreen(
                navigator = { destination -> navController.navigate(destination) }
            )
        }

        composable(AppDestination.CoinDetail) {
            DetailScreen(
                navigator = { destination -> navController.navigate(destination) },
                coinId = it.arguments?.getString(KEY_COIN_ID).orEmpty(),
                onNetworkReconnected = { callback ->
                    onCallBackChange(callback)
                }
            )
        }

        dialog(AppDestination.NoNetwork.route) {
            AppDialogPopUp(
                onDismiss = { navController.popBackStack() },
                onClick = {
                    navController.popBackStack()
                    globalDialogCallback()
                },
                message = stringResource(id = R.string.no_internet_message),
                actionText = stringResource(id = android.R.string.ok),
                title = stringResource(id = R.string.no_internet_title)
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    destination: AppDestination,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = deepLinks,
        content = content
    )
}

fun NavHostController.navigate(destination: AppDestination) {
    when (destination) {
        is AppDestination.Up -> popBackStack()
        else -> navigate(route = destination.destination)
    }
}
