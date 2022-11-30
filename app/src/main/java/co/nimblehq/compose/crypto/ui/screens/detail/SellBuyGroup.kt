package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun SellBuyGroup(
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.colors.coinInfoSellBuyBackground)
            .padding(horizontal = Dp20, vertical = Dp16),
    ) {

        SellBuyButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = Dp12),
            backgroundColor = AppTheme.colors.buttonRedBackground,
            text = stringResource(id = R.string.coin_info_sell_button)
        )

        SellBuyButton(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dp12),
            backgroundColor = AppTheme.colors.buttonBlueBackground,
            text = stringResource(id = R.string.coin_info_buy_button)
        )
    }
}

@Composable
@Preview
fun SellBuyGroupPreview() {
    ComposeTheme {
        SellBuyGroup(modifier = Modifier)
    }
}

@Composable
@Preview
fun SellBuyGroupPreviewDark() {
    ComposeTheme(darkTheme = true) {
        SellBuyGroup(modifier = Modifier)
    }
}
