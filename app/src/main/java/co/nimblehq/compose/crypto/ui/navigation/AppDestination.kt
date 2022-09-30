package co.nimblehq.compose.crypto.ui.navigation

import androidx.navigation.*

const val KEY_COIN_ID = "coinId"

sealed class AppDestination(val route: String) {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    //====================================================//

    object Up : AppDestination("")

    object Home : AppDestination("home")

    object CoinDetail : AppDestination("coin/{$KEY_COIN_ID}") {

        override val arguments = listOf(
            navArgument(KEY_COIN_ID) { type = NavType.StringType }
        )

        fun buildDestination(coinId: String) = apply {
            destination = "coin/$coinId"
        }
    }
}
