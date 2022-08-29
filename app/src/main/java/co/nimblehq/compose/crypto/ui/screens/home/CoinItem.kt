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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.Dimension
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp13
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp22
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp25
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp4
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp60
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.coinItemColor
import co.nimblehq.compose.crypto.ui.theme.Style.coinNameColor
import co.nimblehq.compose.crypto.ui.theme.Style.textColor

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun CoinItem(
    isPositiveNumber: Boolean = false /* TODO Update value to Object on Integrate ticket */
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(Dp12))
            .background(color = MaterialTheme.colors.coinItemColor)
            .padding(horizontal = Dp8, vertical = Dp8)
    ) {
        val (
            logo,
            coinSymbol,
            coinName,
            price,
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
            painter = painterResource(id = R.drawable.ic_btc_bitcoin),
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

        Text(
            modifier = Modifier
                .padding(top = Dp22)
                .constrainAs(price) {
                    start.linkTo(logo.start)
                    top.linkTo(coinName.bottom)
                    width = Dimension.preferredWrapContent
                },
            // TODO: Remove dummy value when work on Integrate.
            text = stringResource(R.string.coin_currency, "24,209"),
            color = MaterialTheme.colors.textColor,
            style = Style.semiBold16()
        )

        PriceChange(
            modifier = Modifier
                .padding(start = Dp25)
                .constrainAs(priceChange) {
                    start.linkTo(price.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.preferredWrapContent
                },
            iconPaddingEnd = Dp13,
            isPositiveNumber = isPositiveNumber
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun CoinItemPreview() {
    ComposeTheme {
        CoinItem()
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun CoinItemPreviewDark() {
    ComposeTheme(darkTheme = true) {
        CoinItem()
    }
}