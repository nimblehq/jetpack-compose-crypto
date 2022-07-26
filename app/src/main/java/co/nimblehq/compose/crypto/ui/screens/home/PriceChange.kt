package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun PriceChange(
    modifier: Modifier,
    isPositiveNumber: Boolean = false
) {
    Row(modifier = modifier) {
        Icon(
            imageVector = if (isPositiveNumber) {
                Icons.Filled.KeyboardArrowUp
            } else {
                Icons.Filled.KeyboardArrowDown
            },
            tint = if (isPositiveNumber) {
                Color.GuppieGreen
            } else {
                Color.FireOpal
            },
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(start = Dp8),
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_profit_percent, "6.21"),
            style = if (isPositiveNumber) {
                Style.guppieGreenMedium16()
            } else {
                Style.fireOpalGreenMedium16()
            }
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun PriceChangePreview() {
    ComposeTheme {
        PriceChange(
            modifier = Modifier
        )
    }
}
