package co.nimblehq.compose.crypto.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.ui.common.price.PriceChange
import co.nimblehq.compose.crypto.ui.preview.CoinItemPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp14
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp25
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp4
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.coinItemColor
import co.nimblehq.compose.crypto.ui.theme.Style.coinNameColor
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun CoinItem(
    coinItem: CoinItemUiModel,
    onMyCoinsItemClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(Dp12))
            .clickable { onMyCoinsItemClick.invoke() }
            .background(color = MaterialTheme.colors.coinItemColor)
            .padding(Dp8)
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
                .size(Dp40)
                .constrainAs(logo) {
                    top.linkTo(anchor = coinSymbol.top, margin = Dp8)
                    bottom.linkTo(anchor = coinName.bottom, margin = Dp8)
                    start.linkTo(parent.start)
                },
            painter = rememberAsyncImagePainter(coinItem.image),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(coinSymbol) {
                    top.linkTo(parent.top)
                    start.linkTo(anchor = logo.end, margin = Dp16)
                },
            text = coinItem.symbol.uppercase(),
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
            text = coinItem.coinName,
            color = MaterialTheme.colors.coinNameColor,
            style = Style.medium14()
        )

        Text(
            modifier = Modifier
                .constrainAs(price) {
                    start.linkTo(logo.start)
                    top.linkTo(anchor = coinName.bottom, margin = Dp14)
                    width = Dimension.preferredWrapContent
                },
            text = stringResource(
                R.string.coin_currency,
                coinItem.currentPrice.toFormattedString()
            ),
            color = MaterialTheme.colors.textColor,
            style = Style.semiBold16()
        )

        PriceChange(
            priceChangePercentage24hInCurrency = coinItem.priceChangePercentage24hInCurrency,
            modifier = Modifier
                .padding(start = Dp25)
                .constrainAs(priceChange) {
                    start.linkTo(price.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.preferredWrapContent
                }
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
fun CoinItemPreview(
    @PreviewParameter(CoinItemPreviewParameterProvider::class) coinItem: CoinItemUiModel
) {
    ComposeTheme {
        CoinItem(
            coinItem = coinItem,
            onMyCoinsItemClick = {}
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun CoinItemPreviewDark(
    @PreviewParameter(CoinItemPreviewParameterProvider::class) coinItem: CoinItemUiModel
) {
    ComposeTheme {
        CoinItem(
            coinItem = coinItem,
            onMyCoinsItemClick = {}
        )
    }
}
