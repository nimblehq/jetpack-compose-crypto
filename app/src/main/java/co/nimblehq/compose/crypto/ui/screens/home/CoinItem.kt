package co.nimblehq.compose.crypto.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import co.nimblehq.compose.crypto.core.extension.toFormattedString
import co.nimblehq.compose.crypto.core.preview.CoinItemPreviewParameterProvider
import co.nimblehq.compose.crypto.core.theme.*
import co.nimblehq.compose.crypto.core.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.domain.model.CoinItem
import coil.compose.rememberAsyncImagePainter

@Composable
fun CoinItem(
    coinItem: CoinItemUiModel,
    onItemClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(Dp12))
            .clickable { onItemClick.invoke() }
            .background(color = AppTheme.colors.coinItemBackground)
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
            color = AppTheme.colors.text,
            style = AppTheme.styles.semiBold16
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
            color = AppTheme.colors.coinNameText,
            style = AppTheme.styles.medium14
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
            color = AppTheme.colors.text,
            style = AppTheme.styles.semiBold16
        )

        co.nimblehq.compose.crypto.core.common.price.PriceChange(
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
            onItemClick = {}
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
            onItemClick = {}
        )
    }
}

fun CoinItem.toUiModel() = CoinItemUiModel(
    id = id,
    symbol = symbol,
    coinName = coinName,
    image = image,
    currentPrice = currentPrice,
    priceChangePercentage24hInCurrency = priceChangePercentage24hInCurrency
)
