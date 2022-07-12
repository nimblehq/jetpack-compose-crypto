package co.nimblehq.compose.crypto.ui.screens.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Color.AlmostWhite
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Color.BlueFreeSpeech

object Palette {
    val ComposeLightPalette = lightColors(
        primary = BlueFreeSpeech,
        background = AlmostWhite
    )

    val ComposeDarkPalette = darkColors()
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
        typography = Typography.ComposeTypography,
        shapes = Shape.ComposeShapes,
        content = content
    )
}
