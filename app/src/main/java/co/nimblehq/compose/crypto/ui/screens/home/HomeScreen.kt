package co.nimblehq.compose.crypto.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.extension.boxShadow
import co.nimblehq.compose.crypto.lib.IsLoading
import co.nimblehq.compose.crypto.ui.navigation.AppDestination
import co.nimblehq.compose.crypto.ui.preview.HomeScreenParams
import co.nimblehq.compose.crypto.ui.preview.HomeScreenPreviewParameterProvider
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp24
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp52
import co.nimblehq.compose.crypto.ui.theme.Dimension.shadowBlurRadius
import co.nimblehq.compose.crypto.ui.theme.Dimension.shadowBorderRadius
import co.nimblehq.compose.crypto.ui.theme.Dimension.shadowOffsetY
import co.nimblehq.compose.crypto.ui.theme.Dimension.shadowSpread
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel
import co.nimblehq.compose.crypto.ui.userReadableMessage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: (destination: AppDestination) -> Unit
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

    val showMyCoinsLoading: IsLoading by viewModel.showMyCoinsLoading.collectAsState()
    val showTrendingCoinsLoading: IsLoading by viewModel.showTrendingCoinsLoading.collectAsState()
    val myCoins: List<CoinItemUiModel> by viewModel.myCoins.collectAsState()
    val trendingCoins: List<CoinItemUiModel> by viewModel.trendingCoins.collectAsState()

    HomeScreenContent(
        showMyCoinsLoading = showMyCoinsLoading,
        showTrendingCoinsLoading = showTrendingCoinsLoading,
        myCoins = myCoins,
        trendingCoins = trendingCoins,
        onMyCoinsItemClick = viewModel.input::onMyCoinsItemClick,
        onTrendingItemClick = viewModel.input::onTrendingCoinsItemClick
    )
}

@Suppress("LongParameterList")
@Composable
private fun HomeScreenContent(
    showMyCoinsLoading: IsLoading,
    showTrendingCoinsLoading: IsLoading,
    myCoins: List<CoinItemUiModel>,
    trendingCoins: List<CoinItemUiModel>,
    onMyCoinsItemClick: (CoinItemUiModel) -> Unit,
    onTrendingItemClick: (CoinItemUiModel) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            LazyColumn {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dp16),
                        text = stringResource(id = R.string.home_title),
                        textAlign = TextAlign.Center,
                        style = Style.semiBold24(),
                        color = MaterialTheme.colors.textColor
                    )
                }

                item {
                    PortfolioCard(
                        modifier = Modifier
                            .padding(start = Dp16, top = Dp40, end = Dp16)
                            .boxShadow(
                                color = Color.TiffanyBlue,
                                borderRadius = shadowBorderRadius,
                                blurRadius = shadowBlurRadius,
                                offsetY = shadowOffsetY,
                                spread = shadowSpread
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
                            style = Style.medium16(),
                            color = MaterialTheme.colors.textColor
                        )

                        SeeAll(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterEnd)
                                .clickable(onClick = { /* TODO: Update on Integrate ticket */ })
                        )
                    }
                }

                if (showTrendingCoinsLoading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally),
                        )
                    }
                } else {
                    items(trendingCoins) { coin ->
                        Box(modifier = Modifier.padding(start = Dp16, end = Dp16, bottom = Dp16)) {
                            TrendingItem(
                                coinItem = coin,
                                onItemClick = { onTrendingItemClick.invoke(coin) }
                            )
                        }
                    }
                }
            }
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
            style = Style.medium16(),
            color = MaterialTheme.colors.textColor
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
                    },
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
                showMyCoinsLoading = isLoading,
                showTrendingCoinsLoading = isLoading,
                myCoins = myCoins,
                trendingCoins = trendingCoins,
                onTrendingItemClick = {},
                onMyCoinsItemClick = {}
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
                showMyCoinsLoading = isLoading,
                showTrendingCoinsLoading = isLoading,
                myCoins = myCoins,
                trendingCoins = trendingCoins,
                onTrendingItemClick = {},
                onMyCoinsItemClick = {}
            )
        }
    }
}
