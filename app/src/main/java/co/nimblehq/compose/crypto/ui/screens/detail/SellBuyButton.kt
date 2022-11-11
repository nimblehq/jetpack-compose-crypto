package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun SellBuyButton(
    modifier: Modifier,
    backgroundColor: Color,
    text: String
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = AppTheme.colors.coinInfoSellBuyText
        ),
        shape = RoundedCornerShape(Dp12),
        elevation = ButtonDefaults.elevation(
            defaultElevation = Dp0,
            pressedElevation = Dp0
        ),
        contentPadding = PaddingValues(vertical = Dp16),
        onClick = { /* TODO */ },
    ) {
        Text(
            text = text,
            style = AppTheme.styles.medium14
        )
    }
}

@Composable
@Preview
fun SellBuyButtonPreview() {
    ComposeTheme {
        SellBuyButton(
            modifier = Modifier,
            backgroundColor = AppTheme.colors.buttonRedBackground,
            text = "Sell"
        )
    }
}
