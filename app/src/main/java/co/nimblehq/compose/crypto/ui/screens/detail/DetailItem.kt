package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.common.price.PriceChange
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun DetailItem(
    modifier: Modifier,
    title: String,
    price: String,
    pricePercent: Double
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (
            itemTitle,
            itemPrice,
            itemPriceChange
        ) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(itemTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            text = title,
            color = AppTheme.colors.coinNameText,
            style = AppTheme.styles.medium12
        )

        Text(
            modifier = Modifier
                .padding(top = Dp4)
                .constrainAs(itemPrice) {
                    start.linkTo(itemTitle.start)
                    top.linkTo(itemTitle.bottom)
                    width = Dimension.preferredWrapContent
                },
            text = stringResource(R.string.coin_currency, price),
            color = AppTheme.colors.text,
            style = AppTheme.styles.medium16
        )

        PriceChange(
            priceChangePercentage24hInCurrency = pricePercent,
            modifier = Modifier
                .constrainAs(itemPriceChange) {
                    end.linkTo(parent.end)
                    top.linkTo(itemTitle.top)
                    bottom.linkTo(itemPrice.bottom)
                },
            displayForDetailPage = true
        )
    }
}

@Composable
@Preview
fun DetailItemPreview() {
    ComposeTheme {
        Surface {
            DetailItem(
                modifier = Modifier,
                title = "Market Cap",
                price = "387,992,058,833.42",
                pricePercent = 7.2323
            )
        }
    }
}

@Composable
@Preview
fun DetailItemPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface {
            DetailItem(
                modifier = Modifier,
                title = "Market Cap",
                price = "387,992,058,833.42",
                pricePercent = -7.2323
            )
        }
    }
}
