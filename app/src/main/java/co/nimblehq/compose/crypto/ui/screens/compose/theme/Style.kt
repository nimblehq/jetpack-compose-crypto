package co.nimblehq.compose.crypto.ui.screens.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Color.LightSilver

object Style {

    @Composable
    fun medium16() = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)

    @Composable
    fun lightSilverMedium16() = medium16().copy(color = LightSilver)

    @Composable
    fun semiBold24() = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.SemiBold)

    @Composable
    fun whiteSemiBold24() = semiBold24().copy(color = White)
}
