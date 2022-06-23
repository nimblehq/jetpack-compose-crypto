package co.nimblehq.compose.crypto.ui.screens.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import co.nimblehq.compose.crypto.R

object Typography {
    private val MontserratFontFamily = FontFamily(
        Font(R.font.montserrat_regular)
    )

    val ComposeTypography = Typography(
        defaultFontFamily = MontserratFontFamily
    )
}
