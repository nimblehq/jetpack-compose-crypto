package co.nimblehq.compose.crypto.ui.screens.detail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.data.extension.orZero
import co.nimblehq.compose.crypto.domain.model.CoinPrice
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.common.price.PriceChangeButton
import co.nimblehq.compose.crypto.ui.components.chartintervals.ChartIntervalsButtonGroup
import co.nimblehq.compose.crypto.ui.components.chartintervals.TimeIntervals
import co.nimblehq.compose.crypto.ui.components.linechart.CoinPriceChart
import co.nimblehq.compose.crypto.ui.components.linechart.CoinPriceLabelDrawer
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.preview.DetailScreenParams
import co.nimblehq.compose.crypto.ui.preview.DetailScreenPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.*
import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel
import co.nimblehq.compose.crypto.ui.userReadableMessage
import coil.compose.rememberAsyncImagePainter
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.GradientLineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation

const val TestTagDetailLogo = "DetailLogo"
const val TestTagDetailCircularProgress = "DetailCircularProgress"
const val TestTagDetailLineChart = "DetailLineChart"
const val TestTagDetailChartInterval = "DetailChartInterval"
const val TestTagDetailCoinInfo = "DetailCoinInfo"
const val TestTagDetailSellBuyGroup = "DetailSellBuyGroup"

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit,
    coinId: String,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.output.error.collect { error ->
            val message = error.userReadableMessage(context)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(viewModel) {
        viewModel.navigator.collect { destination -> navigator(destination) }
    }

    val coinDetailUiModel: CoinDetailUiModel? by viewModel.output.coinDetailUiModel.collectAsState()
    val coinPrices: List<CoinPrice> by viewModel.output.coinPrices.collectAsState()
    val showLoading: IsLoading by viewModel.showLoading.collectAsState()

    DetailScreenContent(
        coinDetailUiModel = coinDetailUiModel,
        coinPrices = coinPrices,
        onBackIconClick = { navigator(AppDestination.Up) },
        onTimeIntervalsChanged = { timeIntervals ->
            viewModel.getCoinPrices(coinId = coinId, timeIntervals)
        },
        showLoading = showLoading
    )

    LaunchedEffect(Unit) {
        viewModel.input.getCoinId(coinId = coinId)
    }
}

@Suppress("LongMethod", "LongParameterList")
@Composable
private fun DetailScreenContent(
    coinDetailUiModel: CoinDetailUiModel?,
    coinPrices: List<CoinPrice>,
    onBackIconClick: () -> Unit,
    onTimeIntervalsChanged: (TimeIntervals) -> Unit,
    showLoading: Boolean
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
                intervals,
                coinInfoItem,
                progressIndicator
            ) = createRefs()

            Appbar(
                modifier = Modifier
                    .constrainAs(appBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                title = coinDetailUiModel?.coinName,
                onBackIconClick = onBackIconClick,
                onRightActionClick = coinDetailUiModel?.let { { /*TODO*/ } }
            )

            coinDetailUiModel?.let {
                Image(
                    modifier = Modifier
                        .size(Dp60)
                        .constrainAs(logo) {
                            top.linkTo(appBar.bottom)
                            linkTo(start = parent.start, end = parent.end)
                        }
                        .padding(top = Dp8)
                        .testTag(tag = TestTagDetailLogo),
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
                    color = AppTheme.colors.text,
                    style = AppTheme.styles.semiBold24
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

                CoinPriceChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(graph) {
                            top.linkTo(priceChangePercent.bottom, margin = Dp24)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .testTag(tag = TestTagDetailLineChart),
                    lineChartData = LineChartData(
                        points = coinPrices.map { coinPrice ->
                            val price = stringResource(
                                R.string.coin_currency,
                                coinPrice.price.toFormattedString()
                            )
                            LineChartData.Point(coinPrice.price.orZero().toFloat(), price)
                        }
                    ),
                    animation = simpleChartAnimation(),
                    pointDrawer = EmptyPointDrawer,
                    lineDrawer = SolidLineDrawer(thickness = 2.dp, color = AppTheme.colors.coinChartLineDrawer),
                    lineShader = GradientLineShader(
                        colors = AppTheme.colors.coinChartLineShaderGradient
                    ),
                    labelDrawer = CoinPriceLabelDrawer(
                        labelTextColorLowest = AppTheme.colors.priceTextNegative,
                        labelTextColorHighest = AppTheme.colors.priceTextPositive
                    ),
                    horizontalOffset = 0f
                )

                // Chart intervals
                ChartIntervalsButtonGroup(
                    modifier = Modifier
                        .constrainAs(intervals) {
                            top.linkTo(graph.bottom, margin = Dp24)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .testTag(tag = TestTagDetailChartInterval),
                    onIntervalChanged = onTimeIntervalsChanged::invoke
                )

                CoinInfo(
                    modifier = Modifier.constrainAs(coinInfoItem) {
                        top.linkTo(intervals.bottom, margin = Dp40)
                    },
                    sellBuyLayoutHeight = sellBuyLayoutHeight.value,
                    coinDetailUiModel = coinDetailUiModel
                )
            }

            if (showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(progressIndicator) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                top = parent.top,
                                bottom = parent.bottom
                            )
                        }
                        .testTag(tag = TestTagDetailCircularProgress),
                )
            }
        }

        coinDetailUiModel?.let {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .testTag(tag = TestTagDetailSellBuyGroup),
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
}

@Composable
private fun CoinInfo(
    modifier: Modifier,
    sellBuyLayoutHeight: Dp,
    coinDetailUiModel: CoinDetailUiModel
) {
    Column(
        modifier = modifier
            .padding(start = Dp16, end = Dp16, bottom = sellBuyLayoutHeight)
            .testTag(tag = TestTagDetailCoinInfo)
    ) {
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
            coinPrices = emptyList(),
            onBackIconClick = {},
            onTimeIntervalsChanged = {},
            showLoading = params.showLoading
        )
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DetailScreenPreviewDark(
    @PreviewParameter(DetailScreenPreviewParameterProvider::class) params: DetailScreenParams
) {
    ComposeTheme {
        DetailScreenContent(
            coinDetailUiModel = params.detail,
            coinPrices = emptyList(),
            onBackIconClick = {},
            onTimeIntervalsChanged = {},
            showLoading = params.showLoading
        )
    }
}
