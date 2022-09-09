package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.Color.DarkJungleGreen
import co.nimblehq.compose.crypto.ui.theme.Color.LightSilver
import co.nimblehq.compose.crypto.ui.theme.Color.Quartz
import co.nimblehq.compose.crypto.ui.theme.Color.QuartzAlpha20
import co.nimblehq.compose.crypto.ui.theme.Color.SonicSilver
import co.nimblehq.compose.crypto.ui.theme.Color.TiffanyBlue
import co.nimblehq.compose.crypto.ui.theme.Color.White
import co.nimblehq.compose.crypto.ui.theme.TextDimension.Sp12
import co.nimblehq.compose.crypto.ui.theme.TextDimension.Sp14
import co.nimblehq.compose.crypto.ui.theme.TextDimension.Sp16
import co.nimblehq.compose.crypto.ui.theme.TextDimension.Sp24

object Style {

    private val textStyle = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_semi_bold, FontWeight.SemiBold)
        )
    )

    val Colors.textColor: Color
        @Composable
        get() = if (isLight) DarkJungleGreen else White

    val Colors.coinItemColor: Color
        @Composable
        get() = if (isLight) White else QuartzAlpha20

    val Colors.coinNameColor: Color
        @Composable
        get() = if (isLight) SonicSilver else LightSilver

    val Colors.coinInfoSellBuyBackground: Color
        @Composable
        get() = if (isLight) White else DarkJungleGreen

    val Colors.coinIfoAppBarIconColor: Color
        @Composable
        get() = if (isLight) Quartz else White

    @Composable
    fun medium12() = textStyle.copy(fontWeight = FontWeight.Medium, fontSize = Sp12)

    @Composable
    fun medium14() = textStyle.copy(fontWeight = FontWeight.Medium, fontSize = Sp14)

    @Composable
    fun tiffanyBlueMedium14() = medium14().copy(color = TiffanyBlue)

    @Composable
    fun medium16() = textStyle.copy(fontWeight = FontWeight.Medium, fontSize = Sp16)

    @Composable
    fun lightSilverMedium16() = medium16().copy(color = LightSilver)

    @Composable
    fun semiBold16() = textStyle.copy(fontWeight = FontWeight.SemiBold, fontSize = Sp16)

    @Composable
    fun semiBold24() = textStyle.copy(fontWeight = FontWeight.SemiBold, fontSize = Sp24)

    @Composable
    fun whiteSemiBold24() = semiBold24().copy(color = White)
}
