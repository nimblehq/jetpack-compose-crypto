@file:Suppress("MatchingDeclarationName")
package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

object Palette {
    val ComposeLightPalette = lightColors(
        surface = Guyabano,
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
