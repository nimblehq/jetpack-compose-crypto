package co.nimblehq.compose.crypto.ui.navigation

sealed class AppDestination(val route: String) {
    object Home : AppDestination("home")
    object CoinDetail : AppDestination("coin_detail")
}
