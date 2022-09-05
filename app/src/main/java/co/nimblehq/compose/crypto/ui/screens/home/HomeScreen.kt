package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp24
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp52
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor
import co.nimblehq.compose.crypto.ui.uimodel.CoinItemUiModel

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val myCoins: List<CoinItemUiModel> by viewModel.myCoins.collectAsState()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
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
                        modifier = Modifier.padding(start = Dp16, top = Dp40, end = Dp16)
                    )
                }

                item { MyCoins(myCoins) }

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

                // TODO: Remove dummy value when work on Integrate.
                items(4) { index ->
                    Box(modifier = Modifier.padding(start = Dp16, end = Dp16, bottom = Dp16)) {
                        if (index == 1) TrendingItem(true) else TrendingItem()
                    }
                }
            }
        }
    }
}

@Suppress("FunctionNaming", "LongMethod", "MagicNumber")
@Composable
private fun MyCoins(coins: List<CoinItemUiModel>) {
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
                CoinItem(coin)
            }
        }
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreen()
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun HomeScreenPreviewDark() {
    ComposeTheme(darkTheme = true) {
        HomeScreen()
    }
}
