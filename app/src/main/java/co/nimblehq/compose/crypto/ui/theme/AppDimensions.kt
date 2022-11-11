package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

class AppDimensions {
    // Card shadow
    val shadowBorderRadius = 12.dp
    val shadowBlurRadius = 24.dp
    val shadowOffsetY = 212.dp
    val shadowSpread = (-6).dp
}

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }
