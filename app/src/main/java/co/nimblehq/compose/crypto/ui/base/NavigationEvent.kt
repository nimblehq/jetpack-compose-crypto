package co.nimblehq.compose.crypto.ui.base

import co.nimblehq.compose.crypto.ui.screens.second.SecondBundle

sealed class NavigationEvent {
    data class Second(val bundle: SecondBundle) : NavigationEvent()
    object Compose : NavigationEvent()
}
