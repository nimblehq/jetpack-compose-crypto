package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import co.nimblehq.compose.crypto.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import co.nimblehq.compose.crypto.ui.common.price.PriceChangeButton
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp0
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp60
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp9
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import coil.compose.rememberAsyncImagePainter

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun DetailScreen() {
    val localDensity = LocalDensity.current
    val sellBuyLayoutHeight = remember { mutableStateOf(Dp0) }

    Surface {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(state = rememberScrollState())
        ) {

            val (
                appBar,
                logo,
                currentPrice,
                priceChangePercent,
                graph,
                coinInfoItem
            ) = createRefs()

            Appbar(modifier = Modifier
                .constrainAs(appBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            Image(
                modifier = Modifier
                    .size(Dp60)
                    .constrainAs(logo) {
                        top.linkTo(appBar.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .padding(top = Dp8),
                // TODO: Remove dummy image when work on Integrate.
                painter = rememberAsyncImagePainter(
                    "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
                ),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .constrainAs(currentPrice) {
                        top.linkTo(logo.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .padding(vertical = Dp8),
                // TODO: Remove dummy value when work on Integrate.
                text = stringResource(R.string.coin_currency, "3,260.62"),
                color = MaterialTheme.colors.textColor,
                style = Style.semiBold24()
            )

            PriceChangeButton(
                modifier = Modifier
                    .constrainAs(priceChangePercent) {
                        top.linkTo(currentPrice.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    },
                iconPaddingEnd = Dp9,
                backgroundColor = Color.WhiteIce,
                // TODO: Remove dummy image when work on Integrate.
                priceChangePercent = "6.21",
                isPositiveNumber = true
            )

            // TODO: Update this section when work create UI for a graph.
            Spacer(
                modifier = Modifier
                    .height(350.dp)
                    .constrainAs(graph) {
                        top.linkTo(priceChangePercent.bottom)
                    }
            )

            CoinInfo(
                modifier = Modifier.constrainAs(coinInfoItem) {
                    top.linkTo(graph.bottom)
                },
                sellBuyLayoutHeight = sellBuyLayoutHeight.value
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomEnd
        ) {
            SellBuyGroup(
                modifier = Modifier.onGloballyPositioned {
                    sellBuyLayoutHeight.value = with(localDensity) { it.size.height.toDp() }
                }
            )
        }
    }
}

@Suppress("FunctionNaming")
@Composable
private fun CoinInfo(
    modifier: Modifier,
    sellBuyLayoutHeight: Dp
) {
    // TODO: Remove dummy value when work on Integrate.
    Column(modifier = modifier.padding(start = Dp16, end = Dp16, bottom = sellBuyLayoutHeight)) {
        DetailItem(
            modifier = Modifier,
            title = "Market Cap",
            price = "387,992,058,833.42",
            pricePercent = 7.32
        )

        DetailItem(
            modifier = Modifier.padding(top = Dp16),
            title = "All Time High",
            price = "4,891.70",
            pricePercent = 33.42
        )

        DetailItem(
            modifier = Modifier.padding(vertical = Dp16),
            title = "All Time Low",
            price = "0.4209",
            pricePercent = 773717.23
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun DetailScreenPreview() {
    ComposeTheme {
        DetailScreen()
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun DetailScreenPreviewDark() {
    ComposeTheme(darkTheme = true) {
        DetailScreen()
    }
}
