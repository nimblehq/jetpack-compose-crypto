package co.nimblehq.compose.crypto.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
            painter = rememberAsyncImagePainter(coinItem.image),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(coinSymbol) {
                    top.linkTo(parent.top)
                    start.linkTo(logo.end)
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
                .padding(top = Dp22)
                .constrainAs(price) {
                    start.linkTo(logo.start)
                    top.linkTo(coinName.bottom)
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
