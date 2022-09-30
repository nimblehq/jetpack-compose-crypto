package co.nimblehq.compose.crypto.ui.navigation

sealed class AppDestination(val route: String = "") {
    open val destination: String = route

    object Up : AppDestination()

    object Home : AppDestination("home")

    data class CoinDetail(val coinId: String) : AppDestination("coin/{coinId}") {
        override val destination = "coin/$coinId"
    }
}
