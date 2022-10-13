package co.nimblehq.compose.crypto.ui.screens.detail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.extension.toFormattedString
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.common.price.PriceChangeButton
import co.nimblehq.compose.crypto.ui.components.chartintervals.ChartIntervalsButtonGroup
import co.nimblehq.compose.crypto.ui.components.linechart.CoinPriceChart
import co.nimblehq.compose.crypto.ui.components.linechart.CoinPriceLabelDrawer
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.preview.DetailScreenParams
import co.nimblehq.compose.crypto.ui.preview.DetailScreenPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp0
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp24
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp60
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import co.nimblehq.compose.crypto.ui.uimodel.CoinDetailUiModel
import co.nimblehq.compose.crypto.ui.userReadableMessage
import coil.compose.rememberAsyncImagePainter
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.GradientLineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation
import kotlin.random.Random

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
    val showLoading: IsLoading by viewModel.showLoading.collectAsState()

    DetailScreenContent(
        coinDetailUiModel = coinDetailUiModel,
        onBackIconClick = { navigator(AppDestination.Up) },
        showLoading = showLoading
    )

    LaunchedEffect(Unit) {
        viewModel.input.getCoinId(coinId = coinId)
    }
}

@Suppress("MagicNumber")
@Composable
private fun DetailScreenContent(
    coinDetailUiModel: CoinDetailUiModel?,
    onBackIconClick: () -> Unit,
    showLoading: Boolean
) {
    val localDensity = LocalDensity.current
    val sellBuyLayoutHeight = remember { mutableStateOf(Dp0) }
    val context = LocalContext.current

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
                val mockData = mutableListOf<LineChartData.Point>()
                for (i in 1..10) {
                    mockData.add(
                        LineChartData.Point(
                            Random.nextInt(18000, 30000).toFloat(),
                            stringResource(R.string.coin_currency, "3,260.62")
                        )
                    )
                }
                CoinPriceChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(graph) {
                            top.linkTo(priceChangePercent.bottom, margin = Dp24)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    lineChartData = LineChartData(
                        points = mockData
                    ),
                    animation = simpleChartAnimation(),
                    pointDrawer = EmptyPointDrawer,
                    lineDrawer = SolidLineDrawer(thickness = 2.dp, color = Color.CaribbeanGreen),
                    lineShader = GradientLineShader(
                        colors = listOf(
                            Color.CaribbeanGreenAlpha30, Color.Transparent
                        )
                    ),
                    labelDrawer = CoinPriceLabelDrawer(
                        labelTextColors = Color.FireOpal to Color.GuppieGreen
                    ),
                    horizontalOffset = 0f
                )

                // Chart intervals
                ChartIntervalsButtonGroup(
                    modifier = Modifier.constrainAs(intervals) {
                        top.linkTo(graph.bottom, margin = Dp24)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    onIntervalChanged = { timeIntervals ->
                        // TODO Refresh the chart on time interval changed
                        Toast.makeText(
                            context,
                            "Time interval changed: $timeIntervals",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
                        },
                )
            }
        }

        coinDetailUiModel?.let {
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
            onBackIconClick = {},
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
            onBackIconClick = {},
            showLoading = params.showLoading
        )
    }
}
