package co.nimblehq.compose.crypto.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import co.nimblehq.compose.crypto.ui.screens.navigation.CryptoNavGraph
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO: Consider update BaseActivity to extends ComponentActivity.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeTheme {
                CryptoNavGraph(this)
            }
        }
    }
}
