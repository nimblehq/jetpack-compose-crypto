package co.nimblehq.compose.crypto.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.extension.boxShadow
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.base.LoadingState
import co.nimblehq.compose.crypto.ui.common.AppDialogPopUp
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.preview.HomeScreenParams
import co.nimblehq.compose.crypto.ui.preview.HomeScreenPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.*
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.ui.userReadableMessage
import timber.log.Timber

private const val LIST_ITEM_LOAD_MORE_THRESHOLD = 0

const val TestTagTrendingItem = "TrendingItem"
const val TestTagCoinItem = "CoinItem"
const val TestTagCoinsLoader = "CoinsLoader"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit
) {
    val context = LocalContext.current
    var rememberRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.output.navigator.collect { destination ->
            navigator(destination)
        }
    }
    LaunchedEffect(viewModel.showLoading) {
        viewModel.showLoading.collect { isRefreshing ->
            rememberRefreshing = isRefreshing
        }
    }

    // TODO remove in integration ticket
    val isNetworkConnected by viewModel.isNetworkConnected.collectAsState()

    val showMyCoinsLoading: IsLoading by viewModel.output.showMyCoinsLoading.collectAsState()
    val showTrendingCoinsLoading: LoadingState by viewModel.output.showTrendingCoinsLoading.collectAsState()
    val myCoins: List<CoinItemUiModel> by viewModel.output.myCoins.collectAsState()
    val trendingCoins: List<CoinItemUiModel> by viewModel.output.trendingCoins.collectAsState()
    val myCoinsError: Throwable? by viewModel.output.myCoinsError.collectAsState()
    val trendingCoinsError: Throwable? by viewModel.output.trendingCoinsError.collectAsState()

    myCoinsError?.let { error ->
        Toast.makeText(context, error.userReadableMessage(context), Toast.LENGTH_SHORT).show()

        viewModel.input.clearMyCoinsError()
    }

    trendingCoinsError?.let { error ->
        Toast.makeText(context, error.userReadableMessage(context), Toast.LENGTH_SHORT).show()

        viewModel.input.clearTrendingCoinsError()
    }

    HomeScreenContent(
        showMyCoinsLoading = showMyCoinsLoading,
        showTrendingCoinsLoading = showTrendingCoinsLoading,
        isRefreshing = rememberRefreshing,
        myCoins = myCoins,
        trendingCoins = trendingCoins,
        onMyCoinsItemClick = viewModel.input::onMyCoinsItemClick,
        onTrendingItemClick = viewModel.input::onTrendingCoinsItemClick,
        onRefresh = { viewModel.input.loadData(isRefreshing = true) },
        onTrendingCoinsLoadMore = { viewModel.input.getTrendingCoins(loadMore = true) }
    )

    // TODO remove in integration ticket
    if (isNetworkConnected == false) {
        AppDialogPopUp(
            onDismiss = { /*TODO*/ },
            onClick = { /*TODO*/ },
            message = stringResource(id = R.string.no_internet_message),
            actionText = stringResource(id = android.R.string.ok),
            title = stringResource(id = R.string.no_internet_title)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Suppress("LongParameterList")
@Composable
private fun HomeScreenContent(
    showMyCoinsLoading: IsLoading,
    showTrendingCoinsLoading: LoadingState,
    isRefreshing: IsLoading,
    myCoins: List<CoinItemUiModel>,
    trendingCoins: List<CoinItemUiModel>,
    onMyCoinsItemClick: (CoinItemUiModel) -> Unit = {},
    onTrendingItemClick: (CoinItemUiModel) -> Unit = {},
    onRefresh: () -> Unit = {},
    onTrendingCoinsLoadMore: () -> Unit = {}
) {
    val refreshingState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh,
        refreshThreshold = PullRefreshDefaults.RefreshThreshold,
        refreshingOffset = PullRefreshDefaults.RefreshThreshold
    )
    val trendingCoinsLastIndex = trendingCoins.lastIndex
    val trendingCoinsState = rememberLazyListState()

    Surface {
        Box(modifier = Modifier.pullRefresh(refreshingState)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                LazyColumn(state = trendingCoinsState) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dp16),
                            text = stringResource(id = R.string.home_title),
                            textAlign = TextAlign.Center,
                            style = AppTheme.styles.semiBold24,
                            color = AppTheme.colors.text
                        )
                    }

                    item {
                        PortfolioCard(
                            modifier = Modifier
                                .padding(start = Dp16, top = Dp40, end = Dp16)
                                .boxShadow(
                                    color = AppTheme.colors.portfolioCardShadow,
                                    borderRadius = AppTheme.dimensions.shadowBorderRadius,
                                    blurRadius = AppTheme.dimensions.shadowBlurRadius,
                                    offsetY = AppTheme.dimensions.shadowOffsetY,
                                    spread = AppTheme.dimensions.shadowSpread
                                )
                        )
                    }

                    item {
                        MyCoins(
                            showMyCoinsLoading = showMyCoinsLoading,
                            coins = myCoins,
                            onMyCoinsItemClick = onMyCoinsItemClick
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = Dp16, top = Dp24, end = Dp16, bottom = Dp16)
                        ) {
                            Text(
                                text = stringResource(id = R.string.home_trending_title),
                                style = AppTheme.styles.medium16,
                                color = AppTheme.colors.text
                            )

                            SeeAll(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterEnd)
                                    .clickable(onClick = { /* TODO: Update on Integrate ticket */ })
                            )
                        }
                    }

                    // Full section loading
                    if (showTrendingCoinsLoading == LoadingState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                                    .testTag(tag = TestTagCoinsLoader),
                            )
                        }
                    } else {
                        itemsIndexed(trendingCoins) { index, coin ->
                            if (index + LIST_ITEM_LOAD_MORE_THRESHOLD >= trendingCoinsLastIndex) {
                                SideEffect {
                                    Timber.d("onTrendingCoinsLoadMore at index: $index, lastIndex: $trendingCoinsLastIndex")
                                    onTrendingCoinsLoadMore.invoke()
                                }
                            }

                            Box(
                                modifier = Modifier.padding(
                                    start = Dp16, end = Dp16, bottom = Dp16
                                )
                            ) {
                                TrendingItem(
                                    modifier = Modifier.testTag(tag = TestTagTrendingItem),
                                    coinItem = coin,
                                    onItemClick = { onTrendingItemClick.invoke(coin) }
                                )
                            }
                        }
                    }

                    // Load more loading
                    if (showTrendingCoinsLoading == LoadingState.LoadingMore) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                                    .padding(bottom = Dp16)
                                    .testTag(tag = TestTagCoinsLoader),
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshingState,
                modifier = Modifier.align(alignment = Alignment.TopCenter),
                backgroundColor = AppTheme.colors.pullRefreshBackground,
                contentColor = AppTheme.colors.pullRefreshContent
            )
        }
    }
}

@Composable
private fun MyCoins(
    showMyCoinsLoading: IsLoading,
    coins: List<CoinItemUiModel>,
    onMyCoinsItemClick: (CoinItemUiModel) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dp52)
    ) {

        val (
            myCoinsTitle,
            seeAllMyCoins,
            myCoins
        ) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(myCoinsTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = Dp16),
            text = stringResource(id = R.string.home_my_coins_title),
            style = AppTheme.styles.medium16,
            color = AppTheme.colors.text
        )

        SeeAll(
            modifier = Modifier
                .clickable(onClick = { /* TODO: Update on Integrate ticket */ })
                .constrainAs(seeAllMyCoins) {
                    top.linkTo(myCoinsTitle.top)
                    end.linkTo(parent.end)
                }
                .padding(end = Dp16)
        )

        if (showMyCoinsLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(myCoins) {
                        top.linkTo(myCoinsTitle.bottom, margin = Dp16)
                        linkTo(start = parent.start, end = parent.end)
                    }
                    .testTag(tag = TestTagCoinsLoader),
            )
        } else {
            LazyRow(
                modifier = Modifier
                    .constrainAs(myCoins) {
                        top.linkTo(myCoinsTitle.bottom, margin = Dp16)
                        start.linkTo(parent.start)
                    },
                contentPadding = PaddingValues(horizontal = Dp16),
                horizontalArrangement = Arrangement.spacedBy(Dp16)
            ) {
                items(coins) { coin ->
                    CoinItem(
                        modifier = Modifier.testTag(tag = TestTagCoinItem),
                        coinItem = coin,
                        onItemClick = { onMyCoinsItemClick.invoke(coin) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_NO)
fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewParameterProvider::class) params: HomeScreenParams
) {
    with(params) {
        ComposeTheme {
            HomeScreenContent(
                showMyCoinsLoading = isMyCoinsLoading,
                showTrendingCoinsLoading = isTrendingCoinsLoading,
                isRefreshing = isMyCoinsLoading,
                myCoins = myCoins,
                trendingCoins = trendingCoins
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDark(
    @PreviewParameter(HomeScreenPreviewParameterProvider::class) params: HomeScreenParams
) {
    with(params) {
        ComposeTheme {
            HomeScreenContent(
                showMyCoinsLoading = isMyCoinsLoading,
                showTrendingCoinsLoading = isTrendingCoinsLoading,
                isRefreshing = isMyCoinsLoading,
                myCoins = myCoins,
                trendingCoins = trendingCoins
            )
        }
    }
}
