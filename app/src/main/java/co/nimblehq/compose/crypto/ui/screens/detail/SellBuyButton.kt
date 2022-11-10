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
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp0
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16

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
            contentColor = White
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
            style = Style.medium14()
        )
    }
}

@Composable
@Preview
fun SellBuyButtonPreview() {
    ComposeTheme {
        SellBuyButton(
            modifier = Modifier,
            backgroundColor = FireOpal,
            text = "Sell"
        )
    }
}
