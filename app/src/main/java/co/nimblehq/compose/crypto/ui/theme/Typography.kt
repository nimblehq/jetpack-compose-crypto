package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import co.nimblehq.compose.crypto.R

object Typography {
    private val InterFontFamily = FontFamily(
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semi_bold, FontWeight.SemiBold)
    )

    val ComposeTypography = Typography(
        defaultFontFamily = InterFontFamily
    )
}
