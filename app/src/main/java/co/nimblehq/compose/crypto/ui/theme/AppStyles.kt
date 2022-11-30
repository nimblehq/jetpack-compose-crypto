package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import co.nimblehq.compose.crypto.R

class AppStyles {

    private val textStyle = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_semi_bold, FontWeight.SemiBold)
        )
    )

    val medium12: TextStyle
        @Composable
        get() = textStyle.copy(fontWeight = FontWeight.Medium, fontSize = Sp12)

    val medium14: TextStyle
        @Composable
        get() = textStyle.copy(fontWeight = FontWeight.Medium, fontSize = Sp14)

    val tiffanyBlueMedium14: TextStyle
        @Composable
        get() = medium14.copy(color = TiffanyBlue)

    val medium16: TextStyle
        @Composable
        get() = textStyle.copy(fontWeight = FontWeight.Medium, fontSize = Sp16)

    val lightSilverMedium16: TextStyle
        @Composable
        get() = medium16.copy(color = LightSilver)

    val semiBold16: TextStyle
        @Composable
        get() = textStyle.copy(fontWeight = FontWeight.SemiBold, fontSize = Sp16)

    val semiBold24: TextStyle
        @Composable
        get() = textStyle.copy(fontWeight = FontWeight.SemiBold, fontSize = Sp24)

    val whiteSemiBold24: TextStyle
        @Composable
        get() = semiBold24.copy(color = White)
}

internal val LocalAppStyles = staticCompositionLocalOf { AppStyles() }
