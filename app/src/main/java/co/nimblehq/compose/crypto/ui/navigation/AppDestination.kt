package co.nimblehq.compose.crypto.ui.navigation

import androidx.navigation.*

const val KEY_COIN_ID = "coinId"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    //====================================================//

    object Up : AppDestination()

    object Home : AppDestination("home")

    /**
     * We can define route as "coin/details" without "coinId" parameter because we're passing it as argument already.
     * So either passing "coinId" via arguments or passing it via route.
     *
     * We keep passing "coinId" in both route and arguments for this destination to give example of navigation concept
     * about how to build a destination with parameters.
     */
    object CoinDetail : AppDestination("coin/{$KEY_COIN_ID}") {

        override val arguments = listOf(
            navArgument(KEY_COIN_ID) { type = NavType.StringType }
        )

        fun buildDestination(coinId: String) = apply {
            destination = "coin/$coinId"
        }
    }
}
