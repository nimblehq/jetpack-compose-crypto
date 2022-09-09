package co.nimblehq.compose.crypto.ui.common.price

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.Color.FireOpal
import co.nimblehq.compose.crypto.ui.theme.Color.GuppieGreen
import co.nimblehq.compose.crypto.ui.theme.Color.Water
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp0
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp13
import co.nimblehq.compose.crypto.ui.theme.Style

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun PriceChangeButton(
    modifier: Modifier,
    iconPaddingEnd: Dp,
    backgroundColor: Color,
    priceChangePercent: String,
    isPositiveNumber: Boolean = false
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = if (isPositiveNumber) GuppieGreen else FireOpal
        ),
        shape = RoundedCornerShape(Dimension.Dp20),
        contentPadding = PaddingValues(start = Dp13, end = Dimension.Dp8),
        elevation = ButtonDefaults.elevation(defaultElevation = Dp0, pressedElevation = Dp0),
        onClick = { /* TODO */ },
    ) {
        Icon(
            modifier = Modifier
                .padding(end = iconPaddingEnd)
                .align(alignment = Alignment.CenterVertically),
            painter = if (isPositiveNumber) {
                painterResource(id = R.drawable.ic_guppie_green_arrow_up)
            } else {
                painterResource(id = R.drawable.ic_fire_opal_arrow_down)
            },
            contentDescription = null
        )

        Text(
            text = stringResource(R.string.coin_profit_percent, priceChangePercent),
            style = Style.medium16()
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun PriceChangeButtonPreview() {
    ComposeTheme {
        PriceChangeButton(
            modifier = Modifier,
            iconPaddingEnd = Dp13,
            backgroundColor = Water,
            priceChangePercent = "6.21"
        )
    }
}
