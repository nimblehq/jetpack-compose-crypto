@file:Suppress("MatchingDeclarationName")
package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*

@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val dimensions = LocalAppDimensions.current

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalAppDimensions provides dimensions
    ) {
        MaterialTheme(
            colors = colors.themeColors,
            shapes = Shape.ComposeShapes,
            content = content
        )
    }
}

/**
 * Alternate to [MaterialTheme] allowing us to add our own theme systems
 * or to extend [MaterialTheme]'s types e.g. return our own [Colors] extension.
 */
object AppTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes

    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimensions.current
}
