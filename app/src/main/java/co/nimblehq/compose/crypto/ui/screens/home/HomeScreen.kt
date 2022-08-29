package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp24
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp52
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun HomeScreen() {
    Surface {
        BoxWithConstraints {
            val screenHeight = maxHeight

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = Dp16)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.home_title),
                    textAlign = TextAlign.Center,
                    style = Style.semiBold24(),
                    color = MaterialTheme.colors.textColor
                )

                PortfolioCard(modifier = Modifier.padding(start = Dp16, top = Dp40, end = Dp16))

                Coins(screenHeight = screenHeight)
            }
        }
    }
}

@Suppress("FunctionNaming", "LongMethod", "MagicNumber")
@Composable
private fun Coins(screenHeight: Dp) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
            .padding(vertical = Dp52)
    ) {

        val (
            myCoinsTitle,
            seeAllMyCoins,
            myCoins,
            trendingTitle,
            seeAllTrending,
            trending
        ) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(myCoinsTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.preferredWrapContent

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
                    width = Dimension.preferredWrapContent
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
            // TODO: Remove dummy value when work on Integrate.
            items(3) { index ->
                if (index == 1) CoinItem(true) else CoinItem()
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(trendingTitle) {
                    top.linkTo(myCoins.bottom, margin = Dp24)
                    start.linkTo(parent.start)
                    width = Dimension.preferredWrapContent

                }
                .padding(start = Dp16),
            text = stringResource(id = R.string.home_trending_title),
            style = Style.medium16(),
            color = MaterialTheme.colors.textColor
        )

        SeeAll(
            modifier = Modifier
                .clickable(onClick = { /* TODO: Update on Integrate ticket */ })
                .constrainAs(seeAllTrending) {
                    top.linkTo(trendingTitle.top)
                    end.linkTo(parent.end)
                    width = Dimension.preferredWrapContent
                }
                .padding(end = Dp16)
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(trending) {
                    top.linkTo(trendingTitle.bottom, margin = Dp16)
                    start.linkTo(parent.start)
                }
                .padding(horizontal = Dp16),
            verticalArrangement = Arrangement.spacedBy(Dp16)
        ) {
            // TODO: Remove dummy value when work on Integrate.
            items(4) { index ->
                if (index == 1) TrendingItem(true) else TrendingItem()
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
