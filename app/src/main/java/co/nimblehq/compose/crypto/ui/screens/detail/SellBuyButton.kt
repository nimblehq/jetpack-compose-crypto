package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

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
            contentColor = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.coinInfoSellBuyText
        ),
        shape = RoundedCornerShape(co.nimblehq.compose.crypto.core.theme.Dp12),
        elevation = ButtonDefaults.elevation(
            defaultElevation = co.nimblehq.compose.crypto.core.theme.Dp0,
            pressedElevation = co.nimblehq.compose.crypto.core.theme.Dp0
        ),
        contentPadding = PaddingValues(vertical = co.nimblehq.compose.crypto.core.theme.Dp16),
        onClick = { /* TODO */ },
    ) {
        Text(
            text = text,
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.medium14
        )
    }
}

@Composable
@Preview
fun SellBuyButtonPreview() {
    co.nimblehq.compose.crypto.core.theme.ComposeTheme {
        SellBuyButton(
            modifier = Modifier,
            backgroundColor = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.buttonRedBackground,
            text = "Sell"
        )
    }
}
