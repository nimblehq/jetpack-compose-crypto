package co.nimblehq.compose.crypto.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.nimblehq.compose.crypto.ui.screens.home.HomeScreen
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

// TODO: Consider update BaseActivity to extends ComponentActivity.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                HomeScreen()
            }
        }
    }
}
