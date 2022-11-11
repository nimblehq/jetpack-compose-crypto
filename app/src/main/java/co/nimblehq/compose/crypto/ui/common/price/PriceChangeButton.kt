package co.nimblehq.compose.crypto.ui.common.price

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.ui.theme.*
import kotlin.math.abs

@Composable
fun PriceChangeButton(
    modifier: Modifier,
    priceChangePercent: Double,
    displayForDetailPage: Boolean = false,
) {
    val isPositiveNumber = priceChangePercent >= 0

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (displayForDetailPage)
                AppTheme.colors.priceChangeButtonBackgroundInDetail else AppTheme.colors.priceChangeButtonBackground,
            contentColor = if (isPositiveNumber)
                AppTheme.colors.priceTextPositive else AppTheme.colors.priceTextNegative
        ),
        shape = RoundedCornerShape(Dp20),
        contentPadding = PaddingValues(start = Dp13, end = Dp8),
        elevation = ButtonDefaults.elevation(defaultElevation = Dp0, pressedElevation = Dp0),
        onClick = { /* TODO */ },
    ) {
        Icon(
            modifier = Modifier
                .padding(end = if (displayForDetailPage) Dp9 else Dp13)
                .align(alignment = Alignment.CenterVertically),
            painter = if (isPositiveNumber) {
                painterResource(id = R.drawable.ic_guppie_green_arrow_up)
            } else {
                painterResource(id = R.drawable.ic_fire_opal_arrow_down)
            },
            contentDescription = null
        )

        Text(
            text = stringResource(
                R.string.coin_profit_percent,
                abs(priceChangePercent).toFormattedString()
            ),
            style = AppTheme.styles.medium16
        )
    }
}

@Composable
@Preview
fun PriceChangeButtonPreview() {
    ComposeTheme {
        PriceChangeButton(
            modifier = Modifier,
            priceChangePercent = 6.2121
        )
    }
}
