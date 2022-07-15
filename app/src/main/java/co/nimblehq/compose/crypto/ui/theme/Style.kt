package co.nimblehq.compose.crypto.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import co.nimblehq.compose.crypto.ui.theme.Color.DarkJungleGreen
import co.nimblehq.compose.crypto.ui.theme.Color.LightSilver
import co.nimblehq.compose.crypto.ui.theme.TextDimension.Sp16
import co.nimblehq.compose.crypto.ui.theme.TextDimension.Sp24

object Style {

    val Colors.titleHome: Color
        @Composable
        get() = if (isLight) DarkJungleGreen else White

    @Composable
    fun medium16() = TextStyle(fontWeight = FontWeight.Medium, fontSize = Sp16)

    @Composable
    fun lightSilverMedium16() = medium16().copy(color = LightSilver)

    @Composable
    fun semiBold24() = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = Sp24)

    @Composable
    fun whiteSemiBold24() = semiBold24().copy(color = White)
}
