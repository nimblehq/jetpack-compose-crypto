package co.nimblehq.compose.crypto.ui.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.Dimension
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.screens.price.PriceChange
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp13
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp4
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.coinNameColor
import co.nimblehq.compose.crypto.ui.theme.Style.textColor

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun CoinInfoItem(
    modifier: Modifier,
    // TODO Update values to Object on Integrate ticket
    coinInfoItemTitle: String,
    coinInfoItemPrice: String,
    coinInfoItemPricePercent: Double
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (
            title,
            price,
            priceChange
        ) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            text = coinInfoItemTitle,
            color = MaterialTheme.colors.coinNameColor,
            style = Style.medium12()
        )

        Text(
            modifier = Modifier
                .padding(top = Dp4)
                .constrainAs(price) {
                    start.linkTo(title.start)
                    top.linkTo(title.bottom)
                    width = Dimension.preferredWrapContent
                },
            text = stringResource(R.string.coin_currency, coinInfoItemPrice),
            color = MaterialTheme.colors.textColor,
            style = Style.medium16()
        )

        PriceChange(
            priceChangePercentage24hInCurrency = coinInfoItemPricePercent,
            modifier = Modifier
                .constrainAs(priceChange) {
                    end.linkTo(parent.end)
                    top.linkTo(title.top)
                    bottom.linkTo(price.bottom)
                },
            iconPaddingEnd = Dp13,
            textStyle = Style.medium14()
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun CoinInfoItemPreview() {
    ComposeTheme {
        Surface {
            CoinInfoItem(
                modifier = Modifier,
                coinInfoItemTitle = "Market Cap",
                coinInfoItemPrice = "387,992,058,833.42",
                coinInfoItemPricePercent = 7.23
            )
        }
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun CoinInfoItemPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface {
            CoinInfoItem(
                modifier = Modifier,
                coinInfoItemTitle = "Market Cap",
                coinInfoItemPrice = "387,992,058,833.42",
                coinInfoItemPricePercent = -7.23
            )
        }
    }
}
