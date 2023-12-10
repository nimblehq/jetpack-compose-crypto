package co.nimblehq.compose.crypto.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import co.nimblehq.compose.crypto.domain.usecase.IsNetworkConnectedUseCase
import co.nimblehq.compose.crypto.rememberCryptoAppState
import co.nimblehq.compose.crypto.ui.navigation.ComposeCryptoApp
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.util.DispatchersProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var isNetworkConnectedUseCase: IsNetworkConnectedUseCase

    @Inject
    lateinit var dispatchersProvider: DispatchersProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeTheme {
                ComposeCryptoApp(
                    cryptoAppState = rememberCryptoAppState(
                        isNetworkConnectedUseCase = isNetworkConnectedUseCase,
                        dispatchersProvider = dispatchersProvider,
                    )
                )
            }
        }
    }
}
