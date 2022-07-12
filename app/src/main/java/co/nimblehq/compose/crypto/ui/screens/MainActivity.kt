package co.nimblehq.compose.crypto.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.nimblehq.compose.crypto.ui.screens.compose.home.HomeScreen
import co.nimblehq.compose.crypto.ui.screens.compose.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

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
