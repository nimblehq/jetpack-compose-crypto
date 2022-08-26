package co.nimblehq.compose.crypto.ui.screens.information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.Color.FireOpal
import co.nimblehq.compose.crypto.ui.theme.Color.TiffanyBlue
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp20
import co.nimblehq.compose.crypto.ui.theme.Style.coinInfoSellBuyBackground

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun CoinInfoSellBuy(
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.coinInfoSellBuyBackground)
            .padding(horizontal = Dp20, vertical = Dp16),
    ) {

        SellBuyButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = Dp12),
            backgroundColor = FireOpal,
            text = stringResource(id = R.string.coin_info_sell_button)
        )

        SellBuyButton(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dp12),
            backgroundColor = TiffanyBlue,
            text = stringResource(id = R.string.coin_info_buy_button)
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun CoinInfoSellBuyPreview() {
    ComposeTheme {
        CoinInfoSellBuy(modifier = Modifier)
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun CoinInfoSellBuyPreviewDark() {
    ComposeTheme(darkTheme = true) {
        CoinInfoSellBuy(modifier = Modifier)
    }
}
