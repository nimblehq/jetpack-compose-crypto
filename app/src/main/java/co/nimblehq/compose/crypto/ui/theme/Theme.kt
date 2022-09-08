package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import co.nimblehq.compose.crypto.ui.theme.Color.BlueFreeSpeech
import co.nimblehq.compose.crypto.ui.theme.Color.DarkJungleGreen
import co.nimblehq.compose.crypto.ui.theme.Color.Guyabano
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Suppress("MatchingDeclarationName")
object Palette {
    val ComposeLightPalette = lightColors(
        primary = BlueFreeSpeech,
        surface = Guyabano,
    )

    val ComposeDarkPalette = darkColors(
        surface = DarkJungleGreen
    )
}

@Suppress("FunctionNaming")
@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        Palette.ComposeDarkPalette
    } else {
        Palette.ComposeLightPalette
    }

    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController, darkTheme) {
        systemUiController.setSystemBarsColor(
            color = colors.surface,
            darkIcons = !darkTheme
        )

        onDispose { }
    }

    MaterialTheme(
        colors = colors,
        shapes = Shape.ComposeShapes,
        content = content
    )
}
