package co.nimblehq.compose.crypto.ui.screens.compose.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.screens.compose.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Dimension.Dp16
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Dimension.Dp40
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Style
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Style.titleHome

@ExperimentalMaterialApi
@Composable
fun HomeScreen() {
    Surface {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dp16)
        ) {
            val (title, portfolioCard) = createRefs()

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
                color = MaterialTheme.colors.titleHome
            )

            PortfolioCard(
                modifier = Modifier.constrainAs(portfolioCard) {
                    top.linkTo(title.bottom, margin = Dp40)
                }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun HomeScreenPreview() {
    ComposeTheme {
        HomeScreen()
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun HomeScreenPreviewDark() {
    ComposeTheme(darkTheme = true) {
        HomeScreen()
    }
}
