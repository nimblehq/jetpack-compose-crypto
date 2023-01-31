package co.nimblehq.compose.crypto.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.ui.common.price.PriceChange
import co.nimblehq.compose.crypto.ui.preview.CoinItemPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp6
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.coinItemColor
import co.nimblehq.compose.crypto.ui.theme.Style.coinNameColor
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import coil.compose.rememberAsyncImagePainter

const val TestTagTrendingItemSymbol = "TrendingItemSymbol"
const val TestTagTrendingItemCoinName = "TrendingItemCoinName"
const val TestTagTrendingItemPriceChange = "TrendingItemPriceChange"

@Suppress("LongMethod")
@Composable
fun TrendingItem(
    modifier: Modifier = Modifier,
    coinItem: CoinItemUiModel,
    onItemClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dp12))
            .clickable { onItemClick.invoke() }
            .background(color = MaterialTheme.colors.coinItemColor)
            .padding(Dp8)
    ) {
        val (
            logo,
            coinSymbol,
            coinName,
            priceChange
        ) = createRefs()

        Image(
            modifier = Modifier
                .size(Dp40)
                .constrainAs(logo) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        topMargin = Dp6,
                        bottomMargin = Dp6
                    )
                    start.linkTo(parent.start)
                },
            painter = rememberAsyncImagePainter(coinItem.image),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(coinSymbol) {
                    top.linkTo(parent.top)
                    bottom.linkTo(coinName.top)
                    start.linkTo(anchor = logo.end, margin = Dp16)
                }
                .testTag(tag = TestTagTrendingItemSymbol),
            text = coinItem.symbol.uppercase(),
            color = MaterialTheme.colors.textColor,
            style = Style.semiBold16()
        )

        Text(
            modifier = Modifier
                .constrainAs(coinName) {
                    start.linkTo(coinSymbol.start)
                    top.linkTo(coinSymbol.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.preferredWrapContent
                }
                .testTag(tag = TestTagTrendingItemCoinName),
            text = coinItem.coinName,
            color = MaterialTheme.colors.coinNameColor,
            style = Style.medium14()
        )

        PriceChange(
            priceChangePercentage24hInCurrency = coinItem.priceChangePercentage24hInCurrency,
            modifier = Modifier
                .constrainAs(priceChange) {
                    end.linkTo(parent.end)
                    top.linkTo(coinSymbol.top)
                    bottom.linkTo(coinName.bottom)
                    width = Dimension.preferredWrapContent
                }
                .testTag(tag = TestTagTrendingItemPriceChange)
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
fun TrendingItemPreview(
    @PreviewParameter(CoinItemPreviewParameterProvider::class) coinItem: CoinItemUiModel
) {
    ComposeTheme {
        TrendingItem(
            coinItem = coinItem,
            onItemClick = {}
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun TrendingItemPreviewDark(
    @PreviewParameter(CoinItemPreviewParameterProvider::class) coinItem: CoinItemUiModel
) {
    ComposeTheme(darkTheme = true) {
        TrendingItem(
            coinItem = coinItem,
            onItemClick = {}
        )
    }
}
