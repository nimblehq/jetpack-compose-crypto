package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import co.nimblehq.compose.crypto.R

object Style {

    private val textStyle = TextStyle(
        fontFamily = FontFamily(
            Font(R.font.inter_medium, FontWeight.Medium),
            Font(R.font.inter_semi_bold, FontWeight.SemiBold)
        )
    )

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
