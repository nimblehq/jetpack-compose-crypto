package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R

@Composable
fun SellBuyGroup(
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(co.nimblehq.compose.crypto.core.theme.AppTheme.colors.coinInfoSellBuyBackground)
            .padding(horizontal = co.nimblehq.compose.crypto.core.theme.Dp20, vertical = co.nimblehq.compose.crypto.core.theme.Dp16),
    ) {

        SellBuyButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = co.nimblehq.compose.crypto.core.theme.Dp12),
            backgroundColor = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.buttonRedBackground,
            text = stringResource(id = R.string.coin_info_sell_button)
        )

        SellBuyButton(
            modifier = Modifier
                .weight(1f)
                .padding(start = co.nimblehq.compose.crypto.core.theme.Dp12),
            backgroundColor = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.buttonBlueBackground,
            text = stringResource(id = R.string.coin_info_buy_button)
        )
    }
}

@Composable
@Preview
fun SellBuyGroupPreview() {
    co.nimblehq.compose.crypto.core.theme.ComposeTheme {
        SellBuyGroup(modifier = Modifier)
    }
}

@Composable
@Preview
fun SellBuyGroupPreviewDark() {
    co.nimblehq.compose.crypto.core.theme.ComposeTheme(darkTheme = true) {
        SellBuyGroup(modifier = Modifier)
    }
}
