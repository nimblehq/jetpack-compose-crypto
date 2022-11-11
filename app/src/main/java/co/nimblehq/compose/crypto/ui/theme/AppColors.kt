package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Extend final [Colors] class to provide more custom app colors.
 */
data class AppColors(
    val themeColors: Colors,

    val textColor: Color,
    val coinItemColor: Color,
    val coinNameColor: Color,
    val coinInfoSellBuyBackground: Color,
    val coinInfoAppBarIconColor: Color,
    val pullRefreshBackgroundColor: Color
)

internal val LightColorPalette = AppColors(
    themeColors = lightColors(
        surface = Guyabano,
    ),
    textColor = DarkJungleGreen,
    coinItemColor = White,
    coinNameColor = SonicSilver,
    coinInfoSellBuyBackground = White,
    coinInfoAppBarIconColor = Quartz,
    pullRefreshBackgroundColor = White
)

internal val DarkColorPalette = AppColors(
    themeColors = darkColors(
        surface = DarkJungleGreen
    ),
    textColor = White,
    coinItemColor = QuartzAlpha20,
    coinNameColor = LightSilver,
    coinInfoSellBuyBackground = DarkJungleGreen,
    coinInfoAppBarIconColor = White,
    pullRefreshBackgroundColor = DarkJungleGreen
)

internal val LocalColors = staticCompositionLocalOf { LightColorPalette }
