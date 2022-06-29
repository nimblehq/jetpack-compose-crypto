package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import co.nimblehq.compose.crypto.ui.base.BaseComposeFragment
import co.nimblehq.compose.crypto.ui.screens.compose.composables.home.HomeScreen
import co.nimblehq.compose.crypto.ui.screens.compose.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class HomeFragment: BaseComposeFragment() {

    override val composeScreen: @Composable () -> Unit
        get() = {
            ComposeTheme {
                HomeScreen()
            }
        }

    override fun bindViewModel() {
        // TODO("Not yet implemented")
    }
}
