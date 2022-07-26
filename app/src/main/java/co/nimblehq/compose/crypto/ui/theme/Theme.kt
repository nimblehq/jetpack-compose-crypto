package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import co.nimblehq.compose.crypto.ui.theme.Color.Cultured
import co.nimblehq.compose.crypto.ui.theme.Color.BlueFreeSpeech
import co.nimblehq.compose.crypto.ui.theme.Color.DarkJungleGreen

object Palette {
    val ComposeLightPalette = lightColors(
        primary = BlueFreeSpeech,
        surface = Cultured
    )

    val ComposeDarkPalette = darkColors(
        surface = DarkJungleGreen
    )
}

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

    MaterialTheme(
        colors = colors,
        shapes = Shape.ComposeShapes,
        content = content
    )
}
