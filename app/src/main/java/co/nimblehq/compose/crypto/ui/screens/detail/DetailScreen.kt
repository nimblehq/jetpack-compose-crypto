package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.ui.common.price.PriceChangeButton
import co.nimblehq.compose.crypto.ui.preview.DetailScreenParams
import co.nimblehq.compose.crypto.ui.preview.DetailScreenPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp0
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp60
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel,
    id: String,
) {
    val coinDetailUiModel: CoinDetailUiModel? by viewModel.output.coinDetailUiModel.collectAsState()

    coinDetailUiModel?.let {
        DetailScreenContent(
            coinDetailUiModel = it,
            onBackIconClick = { navController.popBackStack() }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.input.getCoinId(coinId = id)
    }
}

@Composable
fun DetailScreenContent(
    coinDetailUiModel: CoinDetailUiModel,
    onBackIconClick: () -> Unit
) {
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

            Appbar(
                modifier = Modifier
                    .constrainAs(appBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                title = coinDetailUiModel.coinName,
                onBackIconClick = onBackIconClick
            )

            Image(
                modifier = Modifier
                    .size(Dp60)
                    .constrainAs(logo) {
                        top.linkTo(appBar.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .padding(top = Dp8),
                painter = rememberAsyncImagePainter(coinDetailUiModel.image),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .constrainAs(currentPrice) {
                        top.linkTo(logo.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .padding(vertical = Dp8),
                text = stringResource(
                    R.string.coin_currency,
                    coinDetailUiModel.currentPrice.toFormattedString()
                ),
                color = MaterialTheme.colors.textColor,
                style = Style.semiBold24()
            )

            PriceChangeButton(
                modifier = Modifier
                    .constrainAs(priceChangePercent) {
                        top.linkTo(currentPrice.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    },
                priceChangePercent = coinDetailUiModel.priceChangePercentage24hInCurrency,
                displayForDetailPage = true
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
                sellBuyLayoutHeight = sellBuyLayoutHeight.value,
                coinDetailUiModel = coinDetailUiModel
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

@Composable
private fun CoinInfo(
    modifier: Modifier,
    sellBuyLayoutHeight: Dp,
    coinDetailUiModel: CoinDetailUiModel
) {
    Column(modifier = modifier.padding(start = Dp16, end = Dp16, bottom = sellBuyLayoutHeight)) {
        DetailItem(
            modifier = Modifier,
            title = stringResource(id = R.string.detail_market_cap_title),
            price = coinDetailUiModel.marketCap.toFormattedString(),
            pricePercent = coinDetailUiModel.marketCapChangePercentage24h
        )

        DetailItem(
            modifier = Modifier.padding(top = Dp16),
            title = stringResource(id = R.string.detail_all_time_high_title),
            price = coinDetailUiModel.ath.toFormattedString(),
            pricePercent = coinDetailUiModel.athChangePercentage
        )

        DetailItem(
            modifier = Modifier.padding(vertical = Dp16),
            title = stringResource(id = R.string.detail_all_time_low_title),
            price = coinDetailUiModel.atl.toFormattedString(),
            pricePercent = coinDetailUiModel.atlChangePercentage
        )
    }
}

@Composable
@Preview
fun DetailScreenPreview(
    @PreviewParameter(DetailScreenPreviewParameterProvider::class) params: DetailScreenParams
) {
    ComposeTheme {
        DetailScreenContent(
            coinDetailUiModel = params.detail,
            onBackIconClick = {}
        )
    }
}

@Composable
@Preview
fun DetailScreenPreviewDark(
    @PreviewParameter(DetailScreenPreviewParameterProvider::class) params: DetailScreenParams
) {
    ComposeTheme(darkTheme = true) {
        DetailScreenContent(
            coinDetailUiModel = params.detail,
            onBackIconClick = {}
        )
    }
}
