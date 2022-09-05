package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp13
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp4
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp60
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.coinItemColor
import co.nimblehq.compose.crypto.ui.theme.Style.coinNameColor
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import coil.compose.rememberAsyncImagePainter

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun TrendingItem(
    isPositiveNumber: Boolean = false /* TODO Update value to Object on Integrate ticket */
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dp12))
            .background(color = MaterialTheme.colors.coinItemColor)
            .padding(horizontal = Dp8, vertical = Dp8)
    ) {
        val (
            logo,
            coinSymbol,
            coinName,
            priceChange
        ) = createRefs()

        Image(
            modifier = Modifier
                .size(Dp60)
                .padding(end = Dp16)
                .constrainAs(logo) {
                    top.linkTo(coinSymbol.top)
                    bottom.linkTo(coinName.bottom)
                    start.linkTo(parent.start)
                },
            // TODO: Remove dummy image when work on Integrate.
            painter = rememberAsyncImagePainter(
                "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
            ),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(coinSymbol) {
                    top.linkTo(parent.top)
                    start.linkTo(logo.end)
                },
            // TODO: Remove dummy value when work on Integrate.
            text = "BTC",
            color = MaterialTheme.colors.textColor,
            style = Style.semiBold16()
        )

        Text(
            modifier = Modifier
                .padding(top = Dp4)
                .constrainAs(coinName) {
                    start.linkTo(coinSymbol.start)
                    top.linkTo(coinSymbol.bottom)
                    width = Dimension.preferredWrapContent
                },
            // TODO: Remove dummy value when work on Integrate.
            text = "Bitcoin",
            color = MaterialTheme.colors.coinNameColor,
            style = Style.medium14()
        )

        PriceChange(
            coinItem = coinItemPreview,
            modifier = Modifier
                .constrainAs(priceChange) {
                    end.linkTo(parent.end)
                    top.linkTo(coinSymbol.top)
                    bottom.linkTo(coinName.bottom)
                    width = Dimension.preferredWrapContent
                },
            iconPaddingEnd = Dp13
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun TrendingItemPreview() {
    ComposeTheme {
        TrendingItem()
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun TrendingItemPreviewDark() {
    ComposeTheme(darkTheme = true) {
        TrendingItem()
    }
}
