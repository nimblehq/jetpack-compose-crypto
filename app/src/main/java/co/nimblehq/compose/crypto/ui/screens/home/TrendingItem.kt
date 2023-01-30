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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.core.preview.CoinItemPreviewParameterProvider
import co.nimblehq.compose.crypto.core.uimodel.CoinItemUiModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun TrendingItem(
    coinItem: CoinItemUiModel,
    onItemClick: () -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(co.nimblehq.compose.crypto.core.theme.Dp12))
            .clickable { onItemClick.invoke() }
            .background(color = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.coinItemBackground)
            .padding(co.nimblehq.compose.crypto.core.theme.Dp8)
    ) {
        val (
            logo,
            coinSymbol,
            coinName,
            priceChange
        ) = createRefs()

        Image(
            modifier = Modifier
                .size(co.nimblehq.compose.crypto.core.theme.Dp40)
                .constrainAs(logo) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        topMargin = co.nimblehq.compose.crypto.core.theme.Dp6,
                        bottomMargin = co.nimblehq.compose.crypto.core.theme.Dp6
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
                    start.linkTo(anchor = logo.end, margin = co.nimblehq.compose.crypto.core.theme.Dp16)
                },
            text = coinItem.symbol.uppercase(),
            color = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.text,
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.semiBold16
        )

        Text(
            modifier = Modifier
                .constrainAs(coinName) {
                    start.linkTo(coinSymbol.start)
                    top.linkTo(coinSymbol.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.preferredWrapContent
                },
            text = coinItem.coinName,
            color = co.nimblehq.compose.crypto.core.theme.AppTheme.colors.coinNameText,
            style = co.nimblehq.compose.crypto.core.theme.AppTheme.styles.medium14
        )

        co.nimblehq.compose.crypto.core.common.price.PriceChange(
            priceChangePercentage24hInCurrency = coinItem.priceChangePercentage24hInCurrency,
            modifier = Modifier
                .constrainAs(priceChange) {
                    end.linkTo(parent.end)
                    top.linkTo(coinSymbol.top)
                    bottom.linkTo(coinName.bottom)
                    width = Dimension.preferredWrapContent
                }
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_NO)
fun TrendingItemPreview(
    @PreviewParameter(CoinItemPreviewParameterProvider::class) coinItem: CoinItemUiModel
) {
    co.nimblehq.compose.crypto.core.theme.ComposeTheme {
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
    co.nimblehq.compose.crypto.core.theme.ComposeTheme(darkTheme = true) {
        TrendingItem(
            coinItem = coinItem,
            onItemClick = {}
        )
    }
}
