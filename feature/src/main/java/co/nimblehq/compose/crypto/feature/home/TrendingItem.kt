package co.nimblehq.compose.crypto.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.core.common.price.PriceChange
import co.nimblehq.compose.crypto.core.theme.*
import co.nimblehq.compose.crypto.core.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.feature.home.preview.CoinItemPreviewParameterProvider
import coil.compose.rememberAsyncImagePainter

const val TestTagTrendingItemSymbol = "TrendingItemSymbol"
const val TestTagTrendingItemCoinName = "TrendingItemCoinName"
const val TestTagTrendingItemPriceChange = "TrendingItemPriceChange"

@Suppress("LongMethod")
@Composable
fun TrendingItem(
    coinItem: CoinItemUiModel,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dp12))
            .clickable { onItemClick.invoke() }
            .background(color = AppTheme.colors.coinItemBackground)
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
            color = AppTheme.colors.text,
            style = AppTheme.styles.semiBold16
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
            color = AppTheme.colors.coinNameText,
            style = AppTheme.styles.medium14
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
