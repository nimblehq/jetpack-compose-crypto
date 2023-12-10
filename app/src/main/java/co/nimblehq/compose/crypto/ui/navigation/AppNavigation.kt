package co.nimblehq.compose.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.common.AppDialogPopUp
import co.nimblehq.compose.crypto.ui.common.DialogActionModel
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = AppDestination.Home.destination,
) {

    var dialogActions: List<DialogActionModel> = emptyList()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestination.Home) {
            HomeScreen(
                navigator = { destination -> navController.navigate(destination) },
                onShowGlobalDialog = { actions ->
                    dialogActions = actions
                }
            )
        }

        composable(AppDestination.CoinDetail) {
            DetailScreen(
                navigator = { destination -> navController.navigate(destination) },
                coinId = it.arguments?.getString(KEY_COIN_ID).orEmpty(),
                onShowGlobalDialog = { actions ->
                    dialogActions = actions
                }
            )
        }

        dialog(AppDestination.NoNetwork.route) {
            AppDialogPopUp(
                onDismiss = { navController.popBackStack() },
                onClickAction = {
                    navController.popBackStack()
                },
                message = stringResource(id = R.string.no_internet_message),
                title = stringResource(id = R.string.no_internet_title),
                dialogActions = dialogActions
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
