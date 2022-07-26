package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp52
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor

@Composable
fun HomeScreen() {
    Surface {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dp16)
        ) {
            val (
                title,
                portfolioCard,
                myCoinsTitle,
                seeAll,
                myCoins
            ) = createRefs()

            Text(
                modifier = Modifier
                    .padding(start = Dp16, top = Dp16, end = Dp16)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        linkTo(start = parent.start, end = parent.end)
                        width = Dimension.preferredWrapContent
                    },
                text = stringResource(id = R.string.home_title),
                textAlign = TextAlign.Center,
                style = Style.semiBold24(),
                color = MaterialTheme.colors.textColor
            )

            PortfolioCard(
                modifier = Modifier.constrainAs(portfolioCard) {
                    top.linkTo(title.bottom, margin = Dp40)
                }
            )

            Text(
                modifier = Modifier.constrainAs(myCoinsTitle) {
                    top.linkTo(portfolioCard.bottom, margin = Dp52)
                    start.linkTo(parent.start)
                    width = Dimension.preferredWrapContent

                },
                text = stringResource(id = R.string.home_my_coins_title),
                style = Style.medium16(),
                color = MaterialTheme.colors.textColor
            )

            SeeAll(
                modifier = Modifier
                    .clickable(onClick = { /* TODO: Update on Integrate ticket */ })
                    .constrainAs(seeAll) {
                        top.linkTo(myCoinsTitle.top)
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    }
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(myCoins) {
                        top.linkTo(myCoinsTitle.bottom, margin = Dp16)
                    },
                horizontalArrangement = Arrangement.spacedBy(Dp16)
            ) {
                // TODO: Remove dummy value when work on Integrate.
                item { CoinItem() }
                item { CoinItem(true) }
                item { CoinItem() }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreen()
    }
}

@Composable
@Preview
fun HomeScreenPreviewDark() {
    ComposeTheme(darkTheme = true) {
        HomeScreen()
    }
}
