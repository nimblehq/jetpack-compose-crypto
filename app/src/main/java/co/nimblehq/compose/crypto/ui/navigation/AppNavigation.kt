package co.nimblehq.compose.crypto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.common.AppDialogPopUp
import co.nimblehq.compose.crypto.ui.screens.detail.DetailScreen
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = AppDestination.Home.destination,
) {

    val onDialogDismissedState = remember { mutableStateOf(false) }

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
                onDialogDismissed = onDialogDismissedState.value
            )
        }

        dialog(AppDestination.NoNetwork.route) {
            AppDialogPopUp(
                onDismiss = {
                    onDialogDismissedState.value = true
                    navController.popBackStack()
                },
                onClick = {
                    navController.popBackStack()
                    onDialogDismissedState.value = true
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
